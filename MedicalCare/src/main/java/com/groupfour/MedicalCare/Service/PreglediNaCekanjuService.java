package com.groupfour.MedicalCare.Service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.groupfour.MedicalCare.Common.db.DbColumnConstants;
import com.groupfour.MedicalCare.Model.Administrator.AdminKlinike;
import com.groupfour.MedicalCare.Model.DTO.PregledDTO;
import com.groupfour.MedicalCare.Model.DTO.PregledNaCekanjuDTO;
import com.groupfour.MedicalCare.Model.Dokumenti.IzvestajOPregledu;
import com.groupfour.MedicalCare.Model.Klinika.Sala;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;
import com.groupfour.MedicalCare.Model.Pregled.Operacija;
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
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class PreglediNaCekanjuService {
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
        if(salaJeSlobodnaZaTermin(sala, pregledNaCekanju.getTerminPregleda(), pregledNaCekanju.getTrajanjePregleda()))
        {
            return dodeliSaluPregledu(pregledNaCekanjuDTO);
        }
        return zakaziPregledZaPrviSlobodniTermin(sala, pregledNaCekanju);
    }

    public static ResponseEntity<?> zakaziPregledZaPrviSlobodniTermin(Sala sala, PreglediNaCekanju pregledNaCekanju){
        // Proizvoljni algoritam za odredjivanje termina sale
        // upisi pregled na cekanju u bazu sa izmenjenim podatkom o terminu i sali
        // posalji mejl
        // vrati HttpStatus.OK
        posaljiMejlLekaruIPacijentu(pregledNaCekanju);
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }

    public static ResponseEntity<?> dodeliSaluPregledu(PregledNaCekanjuDTO pregledNaCekanjuDTO){
        PreglediNaCekanju pregledNaCekanju =
                preglediNaCekanjuRepository.getPregledNaCekanjuById(pregledNaCekanjuDTO.getId());
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

    // Trebaju mi informacije o Lekaru i Pacijentu koji zakazuje pregled
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
}
