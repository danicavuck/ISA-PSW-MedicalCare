package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.Administrator.AdminKlinike;
import com.groupfour.MedicalCare.Model.DTO.DodavanjeSaleDTO;
import com.groupfour.MedicalCare.Model.DTO.OperacijaDTO;
import com.groupfour.MedicalCare.Model.Klinika.Klinika;
import com.groupfour.MedicalCare.Model.Klinika.Sala;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;
import com.groupfour.MedicalCare.Model.Pregled.Operacija;
import com.groupfour.MedicalCare.Repository.*;
import com.groupfour.MedicalCare.Utill.CustomEmailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class OperacijaService {
    private static final String emailAddress = "medicalcarepsw@gmail.com";
    private static OperacijaRepository operacijaRepository;
    private static AdminKlinikeRepository adminKlinikeRepository;
    private static PacijentRepository pacijentRepository;
    private static LekarRepository lekarRepository;
    private static SalaRepository salaRepository;
    private static CustomEmailSender customEmailSender;
    private static Logger logger = LoggerFactory.getLogger(OperacijaService.class);

    @Autowired
    public OperacijaService(OperacijaRepository operacijaRepo, LekarRepository lRepo,
                            PacijentRepository pacRepository, CustomEmailSender cEmailSender,
                            AdminKlinikeRepository adminKRepository, SalaRepository sRepo){
        operacijaRepository = operacijaRepo;
        lekarRepository = lRepo;
        pacijentRepository = pacRepository;
        customEmailSender = cEmailSender;
        adminKlinikeRepository = adminKRepository;
        salaRepository = sRepo;
    }

    public static ResponseEntity<?> dodajOperaciju(OperacijaDTO operacijaDTO, HttpSession session){
        int[] klinikaIdLekarID = nadjiIdKlinike(session);
        if(klinikaIdLekarID[0] != -1 && klinikaIdLekarID[1] != -1)
        {
            Pacijent pacijent = pacijentRepository.findPacijentById(operacijaDTO.getPacijentId());
            Lekar lekar = lekarRepository.findLekarById((int) session.getAttribute("id"));
            Set<Lekar> lekari = new HashSet<>();
            lekari.add(lekar);
            LocalDateTime datumVreme = operacijaDTO.getDatumVreme();
            int trajanje = operacijaDTO.getTrajanjeOperacije();

            Operacija operacija =
                    Operacija.builder().pacijent(pacijent).lekar(lekari).terminOperacije(datumVreme).trajanjeOperacije(trajanje).aktivan(false).build();
            lekar.getListaOperacija().add(operacija);
            posaljiMejlAdminima(lekar, datumVreme);
            operacijaRepository.save(operacija);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
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

    public static void posaljiMejlAdminima(Lekar lekar, LocalDateTime datumVreme) {
        ArrayList<AdminKlinike> adminiKlinike = nadjiAdminaKlinikePrekoKlinike(lekar.getKlinika());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
        String message =
                "<h3>Sala za pregled</h3><p>Postovani,</p><p>Lekar " + lekar.getIme() + " " + lekar.getPrezime() + " " +
                        "zeli da zakaze operaciju pacijenta za datum " + formatter.format(datumVreme) + "h. Molimo " +
                        "Vas da dodelite salu za operaciju</p><p>Srdacan pozdrav,</p></p>Medical Care</p>";

        String[] adminEmail = new String[adminiKlinike.size()];
        int i = 0;
        for(AdminKlinike adminKlinike : adminiKlinike)
        {
            adminEmail[i] = adminKlinike.getEmail();
            i++;
        }
        customEmailSender.sendMail(adminEmail, "Zahtev za operaciju", message);
        logger.info("Sending email from: " +  emailAddress);
    }

    public static ArrayList<AdminKlinike> nadjiAdminaKlinikePrekoKlinike(Klinika klinika)
    {
        ArrayList<AdminKlinike> adminiKlinike = adminKlinikeRepository.findAll();
        ArrayList<AdminKlinike> odabraniAdmini = new ArrayList<>();
        for(AdminKlinike admin : adminiKlinike)
        {
            if(admin.getKlinika().getId() == klinika.getId())
                odabraniAdmini.add(admin);
        }
        return odabraniAdmini;
    }

    public static ResponseEntity<?> vratiOperacijeNaCekanjuZaOdgovarajucuKliniku(HttpSession session) {
        AdminKlinike adminKlinike = adminKlinikeRepository.findAdminKlinikeById((int) session.getAttribute("id"));
        if(adminKlinike != null)
        {
            ArrayList<Operacija> operacije = operacijaRepository.findAll();
            ArrayList<OperacijaDTO> naCekanju = new ArrayList<>();
            for(Operacija operacija : operacije)
            {
                if(!operacija.isAktivan())
                    naCekanju.add(mapirajOperaciju(operacija));
            }
            return new ResponseEntity<>(naCekanju, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public static OperacijaDTO mapirajOperaciju(Operacija operacija){
        LocalDateTime pocetakTermina = operacija.getTerminOperacije();
        LocalDateTime krajTermina = pocetakTermina.plusMinutes(operacija.getTrajanjeOperacije());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
        String pocetak = formatter.format(pocetakTermina);
        String kraj = formatter.format(krajTermina);

        OperacijaDTO operacijaDTO =
                OperacijaDTO.builder().pocetakTermina(pocetak).krajTermina(kraj).trajanjeOperacije(operacija.getTrajanjeOperacije()).pacijentId(operacija.getPacijent().getId()).id(operacija.getId()).build();
        Set<Lekar> lekari = operacija.getLekar();
        for(Lekar lekar : lekari) {
            operacijaDTO.setLekarId(lekar.getId());
            operacijaDTO.setImeLekara(lekar.getIme());
            operacijaDTO.setPrezimeLekara(lekar.getPrezime());
        }
        return operacijaDTO;
    }

    @Transactional
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    public static ResponseEntity<?> azurirajOperaciju(DodavanjeSaleDTO dodavanjeSaleDTO, HttpSession session){
        AdminKlinike adminKlinike = adminKlinikeRepository.findAdminKlinikeById((int) session.getAttribute("id"));
        Operacija operacija = operacijaRepository.findOperacijaById(dodavanjeSaleDTO.getOperacijaId());
        Sala sala = salaRepository.findById(dodavanjeSaleDTO.getSalaId());
        if(adminKlinike !=null && operacija != null && sala != null)
        {
            operacija.setSala(sala);
            operacija.setAktivan(true);
            Set<Lekar> lekari = operacija.getLekar();
            Pacijent pacijent = operacija.getPacijent();
            posaljiMejlPacijentuILekaru(adminKlinike, lekari, pacijent, operacija);
            operacijaRepository.save(operacija);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public static void posaljiMejlPacijentuILekaru(AdminKlinike adminKlinike, Set<Lekar> lekari, Pacijent pacijent,
                                                   Operacija operacija) {
        String message =
                "<h3>Sala za pregled</h3><p>Postovani,</p><p>Admin klinike " + adminKlinike.getIme() + " " + adminKlinike.getPrezime() + " " +
                        "je dodao salu " + operacija.getSala().getNazivSale() +" za operaciju pacijenta " + pacijent.getIme() +
                        " " + pacijent.getPrezime() + "</p><p>Srdacan pozdrav,</p></p>Medical Care</p>";


        String lekarEmail = "";
        for(Lekar lekar : lekari) {
            lekarEmail = lekar.getEmail();
            break;
        }
        String[] mejlovi = {pacijent.getEmail(), lekarEmail};
        customEmailSender.sendMail(mejlovi, "Odobrena sala za operaciju", message);
    }
}
