package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.Administrator.AdminKlinike;
import com.groupfour.MedicalCare.Model.DTO.PregledDTO;
import com.groupfour.MedicalCare.Model.DTO.PregledNaCekanjuDTO;
import com.groupfour.MedicalCare.Model.Klinika.Sala;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;
import com.groupfour.MedicalCare.Model.Pregled.Operacija;
import com.groupfour.MedicalCare.Model.Pregled.Pregled;
import com.groupfour.MedicalCare.Model.Pregled.PreglediNaCekanju;
import com.groupfour.MedicalCare.Repository.*;
import com.groupfour.MedicalCare.Utill.CustomEmailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import javax.persistence.PessimisticLockException;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Service
public class PreglediNaCekanjuService {
    private static final long OneDayToMilliseconds = 24 * 60 * 60 * 1000;
    private static PreglediNaCekanjuRepository preglediNaCekanjuRepository;
    private static PregledRepository pregledRepository;
    private static OperacijaRepository operacijaRepository;
    private static AdminKlinikeRepository adminKlinikeRepository;
    private static SalaRepository salaRepository;
    private static CustomEmailSender customEmailSender;
    private static LekarRepository lekarRepository;
    private static Logger logger = LoggerFactory.getLogger(PreglediNaCekanjuService.class);

    @Autowired
    PreglediNaCekanjuService(PreglediNaCekanjuRepository pregledi, SalaRepository sRepository,
                             AdminKlinikeRepository adimnKlinikeRepo, PregledRepository pRepo,
                             OperacijaRepository opeRepo, CustomEmailSender mSender, LekarRepository lRepo){
        preglediNaCekanjuRepository = pregledi;
        salaRepository = sRepository;
        adminKlinikeRepository = adimnKlinikeRepo;
        pregledRepository = pRepo;
        operacijaRepository = opeRepo;
        customEmailSender = mSender;
        lekarRepository = lRepo;
    }

    public static ResponseEntity<?> sviPreglediNaCekanjuZaKliniku(HttpSession session) {
        int klinikaId = dobaviIdKlinike(session);
        ArrayList<PreglediNaCekanju> preglediNaCekanju =
                preglediNaCekanjuRepository.getPreglediNaCekanjuByKlinikaId(klinikaId);
        if(preglediNaCekanju != null){
            ArrayList<PregledDTO> preglediDTO = new ArrayList<>();
            for(PreglediNaCekanju pregled : preglediNaCekanju){
                if(pregled.isAktivan())
                    preglediDTO.add(mapiraniPregled(pregled));
            }

            return new ResponseEntity<>(preglediDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public static int dobaviIdKlinike(HttpSession session){
        AdminKlinike adminKlinike = adminKlinikeRepository.findAdminKlinikeById((int) session.getAttribute("id"));
        if(adminKlinike != null)
        {
            try
            {
                return adminKlinike.getKlinika().getId();
            } catch (Exception e)
            {
                logger.error("Admin klinike ne poseduje kliniku!");
            }
        }
        logger.error("Nije pronadjen nijedan admin klinike");
        return -1;
    }

    public static PregledDTO mapiraniPregled(PreglediNaCekanju preglediNaCekanju){
        LocalDateTime pocetak = preglediNaCekanju.getTerminPregleda();
        LocalDateTime kraj = pocetak.plusMinutes(preglediNaCekanju.getTrajanjePregleda());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
        String pocetakTermina = formatter.format(pocetak);
        String krajTermina = formatter.format(kraj);

        return PregledDTO.builder().id(preglediNaCekanju.getId()).pocetakTermina(pocetakTermina).krajTermina(krajTermina).tipPregleda(preglediNaCekanju.getTipPregleda().getTipPregleda()).build();
    }

    public static ResponseEntity<?> odabirSaleZaPregled(PregledNaCekanjuDTO pregledNaCekanjuDTO, HttpSession session) {
        int klinikaId = dobaviIdKlinike(session);
        PreglediNaCekanju pregledNaCekanju =
                preglediNaCekanjuRepository.getPregledNaCekanjuById(pregledNaCekanjuDTO.getId());
        Sala sala = salaRepository.findByNazivSale(pregledNaCekanjuDTO.getNazivSale());
        if(pregledNaCekanju != null)
        {
            if(salaJeSlobodnaZaTermin(sala, pregledNaCekanju.getTerminPregleda(), pregledNaCekanju.getTrajanjePregleda()))
            {
                return dodeliSaluPregledu(pregledNaCekanjuDTO);
            }
            return zakaziPregledZaPrviSlobodniTermin();
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @Scheduled(fixedDelay = OneDayToMilliseconds, initialDelay = OneDayToMilliseconds)
    public static ResponseEntity<?> zakaziPregledZaPrviSlobodniTermin(){
        // Proizvoljni algoritam za odredjivanje termina sale
        ArrayList<PreglediNaCekanju> sviPreglediNaCekanju = preglediNaCekanjuRepository.findAll();
        ArrayList<PreglediNaCekanju> starijiOdJednogDana = new ArrayList<>();
        LocalDateTime trenutnoVreme = LocalDateTime.now();

        for(PreglediNaCekanju pregled : sviPreglediNaCekanju){
            if(pregled.getTerminPregleda().plusDays(1).isEqual(trenutnoVreme) || pregled.getTerminPregleda().plusDays(1).isBefore(trenutnoVreme) && pregled.isAktivan())
            {
                starijiOdJednogDana.add(pregled);
            }
        }

        for(PreglediNaCekanju pregled : starijiOdJednogDana){
            if(sistemBiraSalu(pregled))
                break;
        }

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    public static boolean sistemBiraSalu(PreglediNaCekanju preglediNaCekanju){
        ArrayList<Sala> sale = salaRepository.findAll();
        int minutesOffset = 0;
        int krajRadnogVremena = 8 * 60;
        while(!zauzmiSaluZaTermin(sale, preglediNaCekanju, minutesOffset))
        {
            if(zauzmiSaluZaTermin(sale, preglediNaCekanju, minutesOffset))
                return true;
            minutesOffset += 30;
            if(minutesOffset >= krajRadnogVremena)
                break;
        }
        return false;
    }

    @Transactional
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    public static boolean zauzmiSaluZaTermin(ArrayList<Sala> sale, PreglediNaCekanju preglediNaCekanju,
                                             int minutesOffset){
        for(Sala sala : sale){
            if(salaJeSlobodnaZaTermin(sala, preglediNaCekanju.getTerminPregleda().plusMinutes(minutesOffset),
                    preglediNaCekanju.getTrajanjePregleda()))
            {
                preglediNaCekanju.setTerminPregleda(preglediNaCekanju.getTerminPregleda().plusMinutes(minutesOffset));
                preglediNaCekanju.setSala(sala);
                preglediNaCekanju.setAktivan(false);
                preglediNaCekanjuRepository.save(preglediNaCekanju);
                kreirajNoviPregledNaOsnovuZahteva(preglediNaCekanju);
                sistemSaljeMejlLekaruIPacijentu(preglediNaCekanju);
                return true;
            }
        }
        return false;
    }

    public static void sistemSaljeMejlLekaruIPacijentu(PreglediNaCekanju preglediNaCekanju){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
        String terminPregleda = formatter.format(preglediNaCekanju.getTerminPregleda());
        int trajanjePregleda = preglediNaCekanju.getTrajanjePregleda();
        String nazivSale = preglediNaCekanju.getSala().getNazivSale();

        Pacijent pacijent = preglediNaCekanju.getPacijent();
        Lekar lekar = preglediNaCekanju.getLekar();
        if(pacijent != null && lekar != null)
        {
            String message =
                    "<html><body><h3>Zakazan pregled</h3><p>Postovani,</p><p>Lekar " + lekar.getIme() + " " + lekar.getPrezime() +
                            " ce obaviti pregled pacijetu " + pacijent.getIme() +" " + pacijent.getPrezime() + " " +
                            "datuma " + terminPregleda + " u trajanju od " + trajanjePregleda + " minuta. " +
                            "</p><p>Sala u kojoj ce se pregled odrzati je: " + nazivSale + "</p><p>Srdacan pozdrav," +
                            "</p><p>Medical Care</p></body></html>";
            String[] adrese = new String[] {pacijent.getEmail(), lekar.getEmail()};
            customEmailSender.sendMail(adrese, "Zakazan pregled", message);
        }
        logger.error("Pacijent i/ili Lekar nisu pronadjeni");
    }

    @Transactional
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    public static ResponseEntity<?> dodeliSaluPregledu(PregledNaCekanjuDTO pregledNaCekanjuDTO){
        PreglediNaCekanju pregledNaCekanju = null;
        try {
            pregledNaCekanju =
                    preglediNaCekanjuRepository.getPregledNaCekanjuById(pregledNaCekanjuDTO.getId());
        } catch (PessimisticLockException exception){
            logger.error("Neuspesno dobavljanje pregleda na cekanju");
            return new ResponseEntity<>(null, HttpStatus.PRECONDITION_FAILED);
        }
        Sala sala = salaRepository.findByNazivSale(pregledNaCekanjuDTO.getNazivSale());
        if(pregledNaCekanju != null && sala != null) {
            pregledNaCekanju.setSala(sala);
            pregledNaCekanju.setAktivan(false);
            preglediNaCekanjuRepository.save(pregledNaCekanju);
            kreirajNoviPregledNaOsnovuZahteva(pregledNaCekanju);
            posaljiMejlLekaruIPacijentu(pregledNaCekanju);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    public static void kreirajNoviPregledNaOsnovuZahteva(PreglediNaCekanju pregledNaCekanju){
        Pregled pregled =
                Pregled.builder().terminPregleda(pregledNaCekanju.getTerminPregleda()).trajanjePregleda(pregledNaCekanju.getTrajanjePregleda()).cena(pregledNaCekanju.getCena()).popust(pregledNaCekanju.getPopust()).aktivan(true).sala(pregledNaCekanju.getSala()).tipPregleda(pregledNaCekanju.getTipPregleda()).pacijent(pregledNaCekanju.getPacijent()).build();

        Lekar lekar = pregledNaCekanju.getLekar();
        lekar.dodajPregled(pregled);
        pregledRepository.save(pregled);
        // cuvanje lekara
        lekarRepository.save(lekar);
    }

    public static boolean salaJeSlobodnaZaTermin(Sala sala, LocalDateTime pocetakTermina, int trajanje){
        LocalDateTime krajTermina = pocetakTermina.plusMinutes(trajanje);

        ArrayList<Pregled> pregledi = pregledRepository.findPregledBySala(sala);
        for(Pregled pregled : pregledi)
        {
            if (pregled.getTerminPregleda().isAfter(pocetakTermina) && pregled.getTerminPregleda().isBefore(krajTermina))
                return false;
        }

        ArrayList<Operacija> operacije = operacijaRepository.findOperacijaBySala(sala);
        for(Operacija operacija : operacije)
        {
            if(operacija.getTerminOperacije().isAfter(pocetakTermina) && operacija.getTerminOperacije().isBefore(krajTermina))
                return false;
        }

        return true;
    }

    public static void posaljiMejlLekaruIPacijentu(PreglediNaCekanju preglediNaCekanju){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
        String terminPregleda = formatter.format(preglediNaCekanju.getTerminPregleda());
        int trajanjePregleda = preglediNaCekanju.getTrajanjePregleda();
        String nazivSale = preglediNaCekanju.getSala().getNazivSale();

        Pacijent pacijent = preglediNaCekanju.getPacijent();
        Lekar lekar = preglediNaCekanju.getLekar();
        if(pacijent != null && lekar != null)
        {
            String message =
                    "<html><body><h3>Zakazan pregled</h3><p>Postovani,</p><p>Lekar " + lekar.getIme() + " " + lekar.getPrezime() +
                            " ce obaviti pregled pacijetu " + pacijent.getIme() +" " + pacijent.getPrezime() + " " +
                            "datuma " + terminPregleda + " u trajanju od " + trajanjePregleda + " minuta. " +
                            "</p><p>Sala u kojoj ce se pregled odrzati je: " + nazivSale + "</p><p>Srdacan pozdrav," +
                            "</p><p>Medical Care</p></body></html>";
            String[] adrese = new String[] {pacijent.getEmail(), lekar.getEmail()};
            customEmailSender.sendMail(adrese, "Zakazan pregled", message);
        }
        logger.error("Pacijent i/ili Lekar nisu pronadjeni");
    }

    public static void helloWorld() {
        logger.info("Hello world");
    }
}
