package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.Administrator.AdminKlinickogCentra;
import com.groupfour.MedicalCare.Model.DTO.DijagnozaDTO;
import com.groupfour.MedicalCare.Model.DTO.KlinikaDTO;
import com.groupfour.MedicalCare.Model.DTO.LekDTO;
import com.groupfour.MedicalCare.Model.DTO.RegistracijaPacijentaDTO;
import com.groupfour.MedicalCare.Model.Dokumenti.SifarnikDijagnoza;
import com.groupfour.MedicalCare.Model.Dokumenti.SifarnikLekova;
import com.groupfour.MedicalCare.Model.Klinika.Klinika;
import com.groupfour.MedicalCare.Model.Zahtevi.RegistracijaPacijenta;
import com.groupfour.MedicalCare.Repository.*;
import com.groupfour.MedicalCare.Utill.CustomEmailSender;
import com.groupfour.MedicalCare.Utill.HibernateUtil;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OptimisticLockException;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminKCService {
    private static AdminKCRepository adminKCRepository;
    private RegistracijaPacijentaRepository registracijaPacijentaRepository;
    private KlinikaRepository klinikaRepository;
    private static Logger logger = LoggerFactory.getLogger(AdminKlinickogCentra.class);
    private SifarnikDijagnozaRepository sifarnikDijagnozaRepository;
    private SifarnikLekovaRepository sifarnikLekovaRepository;

    private static CustomEmailSender customEmailSender;

    @Autowired
    public AdminKCService(AdminKCRepository aKCRepository, RegistracijaPacijentaRepository regRepo,
                          KlinikaRepository kRepo , SifarnikDijagnozaRepository sfRepo,SifarnikLekovaRepository slRepo, CustomEmailSender cmail) {
        adminKCRepository = aKCRepository;
        registracijaPacijentaRepository = regRepo;
        klinikaRepository = kRepo;
        sifarnikDijagnozaRepository = sfRepo;
        sifarnikLekovaRepository = slRepo;
        customEmailSender = cmail;
    }

    public AdminKlinickogCentra getByEmail(String email, String lozinka) {
        AdminKlinickogCentra adminKC = adminKCRepository.findAdminKlinickogCentraByEmail(email);

        if (adminKC.getLozinka().equals(lozinka)) {
            return adminKC;
        }
        return null;
    }

    public AdminKlinickogCentra promenaLozinke(AdminKlinickogCentra adminKlinickogCentra) {
        adminKlinickogCentra.setPrviPutLogovan(false);
        return adminKCRepository.save(adminKlinickogCentra);
    }

    @Transactional(readOnly = false)
    public void prihvatiZahtev(RegistracijaPacijentaDTO registracijaPacijentaDTO, HttpSession session) {
        AdminKlinickogCentra adminKlinickogCentra = adminKCRepository.findAdminKlinickogCentraById((int)session.getAttribute("id"));
        if(adminKlinickogCentra == null){
            logger.error("Nije pronadjen admin klinickog centra");
            return;
        }
        try {
            RegistracijaPacijenta registracijaPacijenta = registracijaPacijentaRepository.getOne(registracijaPacijentaDTO.getId());
            registracijaPacijenta.setOdobren(true);
            registracijaPacijenta.setAktivan(false);
            posaljiMejlKorisniku(registracijaPacijentaDTO,1);
            registracijaPacijentaRepository.save(registracijaPacijenta);
        } catch (OptimisticLockException e) {
            System.out.println("exception");
        }
    }

    @Transactional(readOnly = false)
    public void odbijZahtev(RegistracijaPacijentaDTO registracijaPacijentaDTO, HttpSession session) {
        AdminKlinickogCentra adminKlinickogCentra = adminKCRepository.findAdminKlinickogCentraById((int)session.getAttribute("id"));

        if(adminKlinickogCentra == null){
            logger.error("Nije pronadjen admin klinickog centra");
            return;
        }
        try {
            RegistracijaPacijenta registracijaPacijenta = registracijaPacijentaRepository.getOne(registracijaPacijentaDTO.getId());
            registracijaPacijenta.setOdobren(false);
            registracijaPacijenta.setAktivan(false);
            registracijaPacijenta.setRazlogOdbijanja(registracijaPacijentaDTO.getPoruka());
            posaljiMejlKorisniku(registracijaPacijentaDTO,0);
            registracijaPacijentaRepository.save(registracijaPacijenta);
        } catch (OptimisticLockException e) {
            System.out.println("exception");
            // osvesi sajt
        }

    }

    public static void posaljiMejlKorisniku(RegistracijaPacijentaDTO registracijaPacijentaDTO,int i){

        String message1 = "<html><body><h3>Prihvacen zahtev za registraciju</h3><p>Postovani,</p><p>" + registracijaPacijentaDTO.getIme() + " " + " Vas zahtev za registraciju je prihvacen od strane Administratora klinickog centra.</p><p>Srdacan pozdrav,</p><p>Medical Care</p></body></html>";


        String message0 =
                "<html><body><h3>Razlog odbijanja zahteva za registraciju</h3><p>Postovani,</p><p>" + registracijaPacijentaDTO.getIme() + " " + " Vas zahtev za registraciju je odbijen od strane Administratora klinickog centra. </p><p>Razlog zbog kog je zahtev odbijen: " + registracijaPacijentaDTO.getPoruka() + "</p><p>Srdacan pozdrav,</p><p>Medical Care</p></body></html>";


        String[] adrese = new String[1];
        adrese[0] = registracijaPacijentaDTO.getEmail();
        if(i == 0)
            customEmailSender.sendMail(adrese, "Zahtev za registraciju na Medical Care", message0);
        else
            customEmailSender.sendMail(adrese, "Zahtev za registraciju na Medical Care", message1);

    }

    public ResponseEntity<?> dodajKliniku(KlinikaDTO klinika,HttpSession session) {
            Klinika temp = klinikaRepository.findByNaziv(klinika.getNaziv());
//        AdminKlinickogCentra adminKlinickogCentra = adminKCRepository.findAdminKlinickogCentraById((int)session.getAttribute("id"));
//
//        if(adminKlinickogCentra == null){
//            logger.error("Nije pronadjen admin klinickog centra");
//            return new ResponseEntity<>("Nije nadjen admin klinickog centra",HttpStatus.UNAUTHORIZED);
//        }

        if (temp == null) {
            temp = Klinika.builder().naziv(klinika.getNaziv()).adresa(klinika.getAdresa()).opis(klinika.getOpis()).listaLekara(klinika.getLekari()).listaSestara(klinika.getSestre()).spisakSala(klinika.getSale()).adminiKlinike(klinika.getAdmini()).build();

            klinikaRepository.save(temp);

            return new ResponseEntity<>("Instance created", HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Klinika sa tim nazivom vec postoji!", HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<?> dodajDijagnozu(DijagnozaDTO dijagnozaDTO, HttpSession session) {
         SifarnikDijagnoza temp = sifarnikDijagnozaRepository.findByKodBolesti(dijagnozaDTO.getKodBolesti());
        AdminKlinickogCentra adminKlinickogCentra = adminKCRepository.findAdminKlinickogCentraById((int)session.getAttribute("id"));

      if(adminKlinickogCentra == null){
            logger.error("Nije pronadjen admin klinickog centra");
            return new ResponseEntity<>("Nije nadjen admin klinickog centra",HttpStatus.UNAUTHORIZED);
        }

        if (temp == null) {
            temp = SifarnikDijagnoza.builder().aktivan(true).kodBolesti(dijagnozaDTO.getKodBolesti()).nazivBolesti(dijagnozaDTO.getNazivBolesti()).build();

            sifarnikDijagnozaRepository.save(temp);

            return new ResponseEntity<>("Instance created", HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Dijagnoza tim kodom postoji!", HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<?> dodajLek(LekDTO lekDTO, HttpSession session) {
        SifarnikLekova temp = sifarnikLekovaRepository.findByKodLeka(lekDTO.getKodLeka());
        AdminKlinickogCentra adminKlinickogCentra = adminKCRepository.findAdminKlinickogCentraById((int)session.getAttribute("id"));

        if(adminKlinickogCentra == null){
            logger.error("Nije pronadjen admin klinickog centra");
            return new ResponseEntity<>("Nije nadjen admin klinickog centra",HttpStatus.UNAUTHORIZED);
        }

        if (temp == null) {
            temp = SifarnikLekova.builder().aktivan(true).nazivLeka(lekDTO.getNazivLeka()).kodLeka(lekDTO.getKodLeka()).build();

            sifarnikLekovaRepository.save(temp);

            return new ResponseEntity<>("Instance created", HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Lektim kodom postoji!", HttpStatus.FORBIDDEN);
    }




    public List<SifarnikDijagnoza> getDijagnoze(HttpSession session) {

        List<SifarnikDijagnoza> temp = new ArrayList<>();
        List<SifarnikDijagnoza> all = sifarnikDijagnozaRepository.findAll();

        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).isAktivan()) {
                temp.add(all.get(i));
            }
        }

        return temp;
    }
    public List<SifarnikLekova> getLekovi(HttpSession session) {

        List<SifarnikLekova> temp = new ArrayList<>();
        List<SifarnikLekova> all = sifarnikLekovaRepository.findAll();

        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).isAktivan()) {
                temp.add(all.get(i));
            }
        }

        return temp;
    }

}
