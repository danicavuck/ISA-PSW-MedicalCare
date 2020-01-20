package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.Administrator.AdminKlinike;
import com.groupfour.MedicalCare.Model.DTO.OdsustvaZaAdminaDTO;
import com.groupfour.MedicalCare.Model.DTO.OdsustvoDTO;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Model.Zahtevi.OdsustvoLekara;
import com.groupfour.MedicalCare.Repository.AdminKlinikeRepository;
import com.groupfour.MedicalCare.Repository.LekarRepository;
import com.groupfour.MedicalCare.Repository.OdsustvoLekaraRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

@Service
public class OdsustvaService {
    private static final String emailAddress = "medicalcarepsw@gmail.com";
    private static OdsustvoLekaraRepository odsustvoLekaraRepository;
    private static AdminKlinikeRepository adminKlinikeRepository;
    private static LekarRepository lekarRepository;
    private static JavaMailSender javaMailSender;
    private static Logger logger = LoggerFactory.getLogger(OdsustvaService.class);

    @Autowired
    public OdsustvaService(OdsustvoLekaraRepository odsustvoLekaraRepo, LekarRepository lRepository,
                           JavaMailSender jMailSender, AdminKlinikeRepository adminKRepo){
        odsustvoLekaraRepository = odsustvoLekaraRepo;
        lekarRepository = lRepository;
        javaMailSender = jMailSender;
        adminKlinikeRepository = adminKRepo;
    }

    public static ResponseEntity<?> dodajNoviZahtevZaOdsustvoLekara(OdsustvoDTO odsustvoDTO, HttpSession session) {
        Lekar lekar = lekarRepository.findLekarById((int) session.getAttribute("id"));
        if(lekar != null) {
            OdsustvoLekara odsustvoLekara = OdsustvoLekara.builder().aktivno(true).pocetakOdsustva(odsustvoDTO.getDatumVreme()[0].atStartOfDay()).krajOdsustva(odsustvoDTO.getDatumVreme()[1].atStartOfDay()).odobren(false).lekar(lekar).build();
            odsustvoLekaraRepository.save(odsustvoLekara);
            slanjeMejlaAdminuOZahtevu(lekar, odsustvoDTO);
            return new ResponseEntity<>("Uspesno dodavanje zahteva za odsustvo", HttpStatus.CREATED);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Async
    public static void slanjeMejlaAdminuOZahtevu(Lekar lekar, OdsustvoDTO odsustvoDTO) {
        String pocetakOdsustva =
                odsustvoDTO.getDatumVreme()[0].format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
        String krajOdsustva =
                odsustvoDTO.getDatumVreme()[1].format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        String message =
                "<html><body><h3>Zahtev za odsustvo</h3><p>Postovani,</p><p>Lekar " + lekar.getIme() + " " + lekar.getPrezime() + " zeli da dobije odsustvo u periodu od " + pocetakOdsustva + " do "+ krajOdsustva + "</p><p>Molimo Vas da razmotrite zahtev u dogledno vreme.</p><p>Srdacan pozdrav,</p></p>Medical Care</p></body></html>";

        try {
            helper.setText(message, true);
            helper.setTo("petar.kovacevic0088@gmail.com");
            helper.setSubject("Novi zahtev za odsustvo lekara");
            helper.setFrom(emailAddress);
        } catch (MessagingException e) {
            logger.info("Neuspesno slanje MimeMessage " + e.getMessage());
        }
        javaMailSender.send(mimeMessage);
        logger.info("Sending email from: " +  emailAddress);
    }

    public static ResponseEntity<?> vratiZahteveAdminu(HttpSession session)
    {
        AdminKlinike adminKlinike = adminKlinikeRepository.findAdminKlinikeById((int) session.getAttribute("id"));
        if(adminKlinike != null)
        {
            int klinikaId = 0;
            try
            {
                klinikaId = adminKlinike.getKlinika().getId();
                return vratiZahteveZaOdsustvoLekaraZaKliniku(klinikaId);
            } catch (Exception e)
            {
                logger.info("Admin klinike nema nijednu kliniku");
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        }
        logger.info("Admin klinike nije pronadjen");
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    // Za sada ce vracati sve zahteve, nezavisno od parametra klinikaId
    public static ResponseEntity<?> vratiZahteveZaOdsustvoLekaraZaKliniku(int klinikaId) {
        ArrayList<OdsustvoLekara> odsustvaLekara = odsustvoLekaraRepository.findAll();
        ArrayList<OdsustvaZaAdminaDTO> odsustvaZaAdminaDTO = new ArrayList<>();

        if(odsustvaLekara != null) {
            for(OdsustvoLekara odsustvo : odsustvaLekara) {
                if(odsustvo.isAktivno() && odsustvo.getLekar().getKlinika().getId() == klinikaId){
                    OdsustvaZaAdminaDTO dto =
                            OdsustvaZaAdminaDTO.builder().idOdsustva(odsustvo.getId()).pocetakOdsustva(odsustvo.getPocetakOdsustva().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))).krajOdsustva(odsustvo.getKrajOdsustva().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))).lekar(odsustvo.getLekar().getIme() + " " + odsustvo.getLekar().getPrezime()).build();
                    odsustvaZaAdminaDTO.add(dto);
                }
            }

            return new ResponseEntity<>(odsustvaZaAdminaDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    // Za sada ce vracati sve zahteve, nezavisno od parametra klinikaId
    public static ResponseEntity<?> obrisiZahtevZaOdsustvoLekaraZaKliniku(OdsustvaZaAdminaDTO odsustvaZaAdminaDTO,
                                                                          HttpSession session) {
        int klinikaId = vratiIDKlinike(session);
        if(klinikaId != -1)
        {
            ArrayList<OdsustvoLekara> odsustvaLekara = odsustvoLekaraRepository.findAll();
            for(OdsustvoLekara odsustvo : odsustvaLekara) {
                if(odsustvo.getId() == odsustvaZaAdminaDTO.getIdOdsustva() && odsustvo.getLekar().getKlinika().getId() == klinikaId) {
                    odsustvo.setAktivno(false);
                    odsustvoLekaraRepository.save(odsustvo);
                    return new ResponseEntity<>("Uspesno brisanje", HttpStatus.NO_CONTENT);
                }
            }
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public static int vratiIDKlinike(HttpSession session)
    {
        int klinikaId = -1;
        AdminKlinike adminKlinike = adminKlinikeRepository.findAdminKlinikeById((int) session.getAttribute("id"));
        if(adminKlinike != null)
        {
            try
            {
                klinikaId = adminKlinike.getKlinika().getId();
                return klinikaId;
            } catch (Exception e)
            {
                logger.info("Admin klinike nema kliniku.");
                return -1;
            }
        }
        logger.info("Nije pronadjen admin klinike");
        return klinikaId;
    }

}
