package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.Administrator.AdminKlinike;
import com.groupfour.MedicalCare.Model.DTO.PregledDTO;
import com.groupfour.MedicalCare.Model.Klinika.Klinika;
import com.groupfour.MedicalCare.Model.Klinika.Sala;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Model.Osoblje.MedicinskaSestra;
import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;
import com.groupfour.MedicalCare.Model.Pregled.Pregled;
import com.groupfour.MedicalCare.Model.Pregled.PreglediNaCekanju;
import com.groupfour.MedicalCare.Model.Pregled.TipPregleda;
import com.groupfour.MedicalCare.Repository.*;
import com.groupfour.MedicalCare.Utill.CustomEmailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class PregledService {
    private static final String emailAddress = "medicalcarepsw@gmail.com";
    private static PregledRepository pregledRepository;
    private static PreglediNaCekanjuRepository preglediNaCekanjuRepository;
    private static TipPregledaRepository tipPregledaRepository;
    private static SalaRepository salaRepository;
    private static LekarRepository lekarRepository;
    private static AdminKlinikeRepository adminKlinikeRepository;
    private static PacijentRepository pacijentRepository;
    private static CustomEmailSender customEmailSender;

    private static Logger logger = LoggerFactory.getLogger(PregledService.class);

    @Autowired
    public PregledService(PregledRepository pRepository, TipPregledaRepository tpRepository,
                          SalaRepository sRepository, LekarRepository lRepository,
                          PacijentRepository pacRepository, PreglediNaCekanjuRepository pNaCekanju,
                          CustomEmailSender customEmail, AdminKlinikeRepository adminKlinikeRepo) {
        pregledRepository = pRepository;
        tipPregledaRepository = tpRepository;
        salaRepository = sRepository;
        lekarRepository = lRepository;
        pacijentRepository = pacRepository;
        preglediNaCekanjuRepository = pNaCekanju;
        customEmailSender = customEmail;
        adminKlinikeRepository = adminKlinikeRepo;
    }

    public static ResponseEntity<?> dobaviSvePregledeZaKliniku(HttpSession session) {
        int klinikaId = dobaviIdKlinike(session);
        ArrayList<Pregled> pregledi = pregledRepository.findAll();
        ArrayList<PregledDTO> preglediDTO = new ArrayList<>();

        if(klinikaId != -1)
        {
            for (Pregled p : pregledi) {
                if(p.getSala().getKlinika().getId() == klinikaId && p.isAktivan())
                    preglediDTO.add(mapirajPregledDTO(p));
            }
            return new ResponseEntity<>(preglediDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public static int dobaviIdKlinike(HttpSession session){
        int idKlinike = -1;
        AdminKlinike adminKlinike = adminKlinikeRepository.findAdminKlinikeById((int) session.getAttribute("id"));
        if(adminKlinike != null)
        {
            try
            {
                return adminKlinike.getKlinika().getId();
            } catch (Exception e)
            {
                logger.error("Admin klinike nema kliniku");
                return idKlinike;
            }
        }
        logger.info("Admin klinike nije pronadjen");
        return idKlinike;
    }

    public static ResponseEntity<?> dobaviSvePregledeZaKlinikuMedSestra(HttpSession session) {
        int klinikaId = dobaviIdKlinikeMedSestra(session);
        ArrayList<Pregled> pregledi = pregledRepository.findAll();
        ArrayList<PregledDTO> preglediDTO = new ArrayList<>();

        if(klinikaId != -1)
        {
            for (Pregled p : pregledi) {
                if(p.getSala().getKlinika().getId() == klinikaId && p.isAktivan())
                    preglediDTO.add(mapirajPregledDTO(p));
            }
            return new ResponseEntity<>(preglediDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    public static int dobaviIdKlinikeMedSestra(HttpSession session){
        int idKlinike = -1;
        Lekar lekar = lekarRepository.findLekarById((int) session.getAttribute("id"));
        if(lekar != null){
            try{
                return  lekar.getKlinika().getId();
            }catch (Exception e){
                logger.error("Medicinska sestra nema kliiku");
                return idKlinike;
            }

        }
        logger.info("Medicinska sestra nije pronadjena");
        return idKlinike;
    }



    public static PregledDTO mapirajPregledDTO(Pregled pregled) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
        PregledDTO pregledDTO =
                PregledDTO.builder().trajanjePregleda(pregled.getTrajanjePregleda()).pacijent(pregled.getPacijent().getId()).cena(pregled.getCena()).popust(pregled.getPopust()).sala(pregled.getSala().getNazivSale()).tipPregleda(pregled.getTipPregleda().getTipPregleda()).id(pregled.getId()).salaId(pregled.getSala().getId()).build();
        Set<Lekar> lekari = pregled.getLekari();

        logger.info("Lekari pocetak termina kraj termina i ostalo:\n"+ lekari.toString());

        while (lekari.iterator().hasNext())
        {
            Lekar lekar = lekari.iterator().next();
            LocalDateTime pocetakTermina = pregled.getTerminPregleda();
            long trajanje = pregled.getTrajanjePregleda();
            LocalDateTime krajPregleda = pocetakTermina.plusMinutes(trajanje);

            pregledDTO.setLekar(lekar.getId());
            pregledDTO.setPocetakTermina(pocetakTermina.format(formatter));
            pregledDTO.setKrajTermina(krajPregleda.format(formatter));
            pregledDTO.setLekarImeIPrezime(lekar.getIme() + " " + lekar.getPrezime());
            return pregledDTO;
        }
        return pregledDTO;
    }

    public static ResponseEntity<?> kreirajNoviPregled(PregledDTO pregledDTO) {
        TipPregleda tipPregleda = tipPregledaRepository.findByTipPregleda(pregledDTO.getTipPregleda());
        Sala sala = salaRepository.findById(pregledDTO.getSalaId());
        Lekar lekar = lekarRepository.findLekarById(pregledDTO.getLekar());
        if(tipPregleda != null && sala != null && lekar != null){
            int popust = pregledDTO.getPopust();
            int cena = pregledDTO.getCena();
            int trajanje = pregledDTO.getTrajanjePregleda();
            LocalDateTime datumVreme = pregledDTO.getDatumVreme();
            int pocetakTermina = datumVreme.getHour();
            int krajTermina = datumVreme.plusMinutes(trajanje).getHour();

            if(lekar.getPocetakRadnogVremena() < pocetakTermina && lekar.getKrajRadnogVremena() > krajTermina)
            {
                Pregled pregled = Pregled.builder().aktivan(true).cena(cena).popust(popust).sala(sala).tipPregleda(tipPregleda).trajanjePregleda(trajanje).terminPregleda(datumVreme).build();

                pregledRepository.save(pregled);
                lekar.dodajPregled(pregled);
                lekarRepository.save(lekar);
                return new ResponseEntity<>("Uspesno dodavanje pregleda", HttpStatus.CREATED);
            }

        }
        return new ResponseEntity<>("Nije moguce dodati pregled datom lekaru", HttpStatus.FORBIDDEN);
    }

    public static ResponseEntity<?> zapocniNoviPregled(PregledDTO pregledDTO, HttpSession session) {
        int[] klinikaIdLekarID = nadjiIdKlinike(session);
        if(klinikaIdLekarID[0] != -1 && klinikaIdLekarID[1] != -1)
        {
            TipPregleda tipPregleda = tipPregledaRepository.findByTipPregleda(pregledDTO.getTipPregleda());
            Pacijent pacijent = pacijentRepository.findPacijentById(pregledDTO.getPacijent());
            Lekar lekar = lekarRepository.findLekarById(klinikaIdLekarID[1]);
            int popust = pregledDTO.getPopust();
            int cena = pregledDTO.getCena();
            int trajanje = pregledDTO.getTrajanjePregleda();
            LocalDateTime datumVreme = pregledDTO.getDatumVreme();

            PreglediNaCekanju pregledNaCekanju =
                    PreglediNaCekanju.builder().klinikaId(klinikaIdLekarID[0]).aktivan(true).cena(cena).popust(popust).tipPregleda(tipPregleda).trajanjePregleda(trajanje).terminPregleda(datumVreme).pacijent(pacijent).lekar(lekar).build();
            preglediNaCekanjuRepository.save(pregledNaCekanju);

            try{
                posaljiMejlAdminu(lekar, pregledNaCekanju);
            } catch (MailException exception){
                logger.info("Neuspesno slanje mejla:" + exception.getMessage());
            }

            return new ResponseEntity<>("Uspesno zapocet pregled", HttpStatus.CREATED);
        }
        else
        {
            return new ResponseEntity<>("Bezuspesno zapocet pregled", HttpStatus.NOT_FOUND);
        }
    }

    public static int[] nadjiIdKlinike(HttpSession session){
        int[] klinikaIdLekarID = {-1, -1};
        Lekar lekar = lekarRepository.findLekarById((int) session.getAttribute("id"));
        if (lekar != null)
        {
            try
            {
                klinikaIdLekarID[0] = lekar.getKlinika().getId();
                klinikaIdLekarID[1] = lekar.getId();
            } catch (Exception e)
            {
                logger.info("Lekar nema kliniku");
                return klinikaIdLekarID;
            }
        }
        logger.error("Lekar nije pronadjen");
        return klinikaIdLekarID;
    }

    @Async
    public static void posaljiMejlAdminu(Lekar lekar, PreglediNaCekanju pregledNaCekanju) throws MailException {
        AdminKlinike adminKlinike = nadjiAdminaKlinikePrekoKlinike(lekar.getKlinika());
        if(adminKlinike == null)
        {
            logger.error("Nije pronadjen admin klinike preko klinike");
            return;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
        String message =
                "<h3>Sala za pregled</h3><p>Postovani,</p><p>Lekar " + lekar.getIme() + " " + lekar.getPrezime() + " " +
                        "zeli da zakaze pregled pacijentu za datum " + formatter.format(pregledNaCekanju.getTerminPregleda()) + "h</p><p>Molimo Vas da dodelite salu za pregled</p></br><p>Srdacan pozdrav,</p></p>Medical Care</p>";
        String[] adminEmail = {adminKlinike.getEmail()};
        customEmailSender.sendMail(adminEmail, "Novi zahtev za pregled", message);
        logger.info("Sending email from: " +  emailAddress);
    }

    public static ResponseEntity<?> dobaviPregledeZaPacijenta(Integer pacijentId) {
        Pacijent pacijent = pacijentRepository.findPacijentById(pacijentId);
        Set<Pregled> pregledi = new HashSet<>();
        Set<PregledDTO> pregledDTOS = new HashSet<>();
        if(pacijent != null) {
            pregledi = pacijent.getListaPregleda();
            for(Pregled pregled : pregledi) {
                pregledDTOS.add(mapirajPregledDTO(pregled));
            }
            return new ResponseEntity<>(pregledDTOS, HttpStatus.OK);
        }
        return new ResponseEntity<>(pregledDTOS, HttpStatus.NOT_FOUND);
    }

    public static AdminKlinike nadjiAdminaKlinikePrekoKlinike(Klinika klinika)
    {
        ArrayList<AdminKlinike> adminiKlinike = adminKlinikeRepository.findAll();
        for(AdminKlinike admin : adminiKlinike)
        {
            if(admin.getKlinika().getId() == klinika.getId())
                return admin;
        }
        return null;
    }

    public static ResponseEntity<?> obrisiPregled(PregledDTO pregledDTO){
        Pregled pregled = pregledRepository.findPregledById(pregledDTO.getId());
        if(pregled != null)
        {
            pregled.setAktivan(false);
            pregledRepository.save(pregled);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

}
