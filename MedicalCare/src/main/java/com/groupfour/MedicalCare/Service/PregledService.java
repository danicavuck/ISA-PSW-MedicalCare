package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.Administrator.AdminKlinike;
import com.groupfour.MedicalCare.Model.DTO.PregledDTO;
import com.groupfour.MedicalCare.Model.Klinika.Klinika;
import com.groupfour.MedicalCare.Model.Klinika.Sala;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;
import com.groupfour.MedicalCare.Model.Pregled.Pregled;
import com.groupfour.MedicalCare.Model.Pregled.PreglediNaCekanju;
import com.groupfour.MedicalCare.Model.Pregled.TipPregleda;
import com.groupfour.MedicalCare.Repository.*;
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
    private static PregeldRepository pregledRepository;
    private static PreglediNaCekanjuRepository preglediNaCekanjuRepository;
    private static TipPregledaRepository tipPregledaRepository;
    private static SalaRepository salaRepository;
    private static LekarRepository lekarRepository;
    private static AdminKlinikeRepository adminKlinikeRepository;
    private static PacijentRepository pacijentRepository;
    private static JavaMailSender javaMailSender;
    private static Logger logger = LoggerFactory.getLogger(PregledService.class);

    @Autowired
    public PregledService(PregeldRepository pRepository, TipPregledaRepository tpRepository,
                          SalaRepository sRepository, LekarRepository lRepository,
                          PacijentRepository pacRepository, PreglediNaCekanjuRepository pNaCekanju,
                          JavaMailSender javaMSender, AdminKlinikeRepository adminKlinikeRepo) {
        pregledRepository = pRepository;
        tipPregledaRepository = tpRepository;
        salaRepository = sRepository;
        lekarRepository = lRepository;
        pacijentRepository = pacRepository;
        preglediNaCekanjuRepository = pNaCekanju;
        javaMailSender = javaMSender;
        adminKlinikeRepository = adminKlinikeRepo;
    }

    public static ResponseEntity<?> dobaviSvePregledeZaKliniku(HttpSession session) {
        int klinikaId = dobaviIdKlinike(session);
        ArrayList<Pregled> pregledi = pregledRepository.findAll();
        ArrayList<PregledDTO> preglediDTO = new ArrayList<>();

        if(klinikaId != -1)
        {
            for (Pregled p : pregledi) {
                if(p.getSala().getKlinika().getId() == klinikaId)
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

    public static PregledDTO mapirajPregledDTO(Pregled pregled) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
        PregledDTO pregledDTO =
                PregledDTO.builder().trajanjePregleda(pregled.getTrajanjePregleda()).cena(pregled.getCena()).popust(pregled.getPopust()).sala(pregled.getSala().getNazivSale()).tipPregleda(pregled.getTipPregleda().getTipPregleda()).build();
        Set<Lekar> lekari = pregled.getLekari();
        Lekar lekar = lekari.iterator().next();
        LocalDateTime pocetakTermina = pregled.getTerminPregleda();
        long trajanje = (long) pregled.getTrajanjePregleda();
        LocalDateTime krajPregleda = pocetakTermina.plusMinutes(trajanje);

        pregledDTO.setLekar(lekar.getId());
        pregledDTO.setPocetakTermina(pocetakTermina.format(formatter));
        pregledDTO.setKrajTermina(krajPregleda.format(formatter));
        pregledDTO.setLekarImeIPrezime(lekar.getIme() + " " + lekar.getPrezime());

        return pregledDTO;
    }

    public static void kreirajNoviPregled(PregledDTO pregledDTO) {
        TipPregleda tipPregleda = tipPregledaRepository.findByTipPregleda(pregledDTO.getTipPregleda());
        Sala sala = salaRepository.findByNazivSale(pregledDTO.getSala());
        Lekar lekar = lekarRepository.findLekarById(pregledDTO.getLekar());
        int popust = pregledDTO.getPopust();
        int cena = pregledDTO.getCena();
        int trajanje = pregledDTO.getTrajanjePregleda();
        LocalDateTime datumVreme = pregledDTO.getDatumVreme();

        Pregled pregled = Pregled.builder().aktivan(true).cena(cena).popust(popust).sala(sala).tipPregleda(tipPregleda).trajanjePregleda(trajanje).terminPregleda(datumVreme).build();

        System.out.println(pregled);

        // Rucno cu cuvati entitete jer Cascading nije cuvao polja za pregled kada sam ga dodavao kroz lekara
        pregledRepository.save(pregled);
        lekar.dodajPregled(pregled);
        lekarRepository.save(lekar);

    }

    // Pretpostavka za LekarID = 1, kao i za klinikaId = 5
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
                    PreglediNaCekanju.builder().klinikaId(klinikaIdLekarID[0]).aktivan(true).cena(cena).popust(popust).tipPregleda(tipPregleda).trajanjePregleda(trajanje).terminPregleda(datumVreme).pacijent(pacijent).build();
            preglediNaCekanjuRepository.save(pregledNaCekanju);

            try{
                posaljiMejlAdminu(lekar, pregledNaCekanju, session);
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
    public static void posaljiMejlAdminu(Lekar lekar, PreglediNaCekanju pregledNaCekanju, HttpSession session) throws MailException {
        AdminKlinike adminKlinike = nadjiAdminaKlinikePrekoKlinike(lekar.getKlinika());
        if(adminKlinike == null)
        {
            logger.error("Nije pronadjen admin klinike preko klinike");
            return;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        String message =
                "<h3>Sala za pregled</h3><p>Postovani,</p><p>Lekar " + lekar.getIme() + " " + lekar.getPrezime() + " " +
                        "zeli da zakaze pregled pacijentu za datum " + formatter.format(pregledNaCekanju.getTerminPregleda()) + "h</p><p>Molimo Vas da dodelite salu za pregled</p></br><p>Srdacan pozdrav,</p></p>Medical Care</p>";

        try {
            helper.setText(message, true);
            helper.setTo(adminKlinike.getEmail());
            helper.setSubject("Novi zahtev za pregled");
            helper.setFrom(emailAddress);
        } catch (MessagingException e) {
            logger.info("Neuspesno slanje MimeMessage " + e.getMessage());
        }
        javaMailSender.send(mimeMessage);
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

}
