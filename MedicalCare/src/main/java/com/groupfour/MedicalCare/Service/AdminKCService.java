package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.Administrator.AdminKlinickogCentra;
import com.groupfour.MedicalCare.Model.DTO.KlinikaDTO;
import com.groupfour.MedicalCare.Model.Klinika.Klinika;
import com.groupfour.MedicalCare.Model.Zahtevi.RegistracijaPacijenta;
import com.groupfour.MedicalCare.Repository.AdminKCRepository;
import com.groupfour.MedicalCare.Repository.KlinikaRepository;
import com.groupfour.MedicalCare.Repository.RegistracijaPacijentaRepository;
import com.groupfour.MedicalCare.Utill.HibernateUtil;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OptimisticLockException;

@Service
public class AdminKCService {
    @Autowired
    private static AdminKCRepository adminKCRepository;
    @Autowired
    private RegistracijaPacijentaRepository registracijaPacijentaRepository;
    @Autowired
    private KlinikaRepository klinikaRepository;

    public AdminKCService(AdminKCRepository aKCRepository, RegistracijaPacijentaRepository regRepo,
                          KlinikaRepository kRepo) {
        adminKCRepository = aKCRepository;
        registracijaPacijentaRepository = regRepo;
        klinikaRepository = kRepo;
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
    public void prihvatiZahtev(Integer id) {

        try {
            RegistracijaPacijenta registracijaPacijenta = registracijaPacijentaRepository.getOne(id);
            registracijaPacijenta.setOdobren(true);
            registracijaPacijenta.setAktivan(false);
            System.out.println("u servisu");
            registracijaPacijentaRepository.save(registracijaPacijenta);
        } catch (OptimisticLockException e) {
            System.out.println("exception");
        }
    }

    @Transactional(readOnly = false)
    public void odbijZahtev(Integer id) {
        try {
            RegistracijaPacijenta registracijaPacijenta = registracijaPacijentaRepository.getOne(id);
            registracijaPacijenta.setOdobren(false);
            registracijaPacijenta.setAktivan(false);
            registracijaPacijentaRepository.save(registracijaPacijenta);
        } catch (OptimisticLockException e) {
            System.out.println("exception");
            // osvesi sajt
        }

    }

    public ResponseEntity<String> dodajKliniku(KlinikaDTO klinika) {
        Klinika temp = klinikaRepository.findByNaziv(klinika.getNaziv());
        if (temp == null) {
            temp = Klinika.builder().naziv(klinika.getNaziv()).adresa(klinika.getAdresa()).opis(klinika.getOpis()).listaLekara(klinika.getLekari()).listaSestara(klinika.getSestre()).spisakSala(klinika.getSale()).adminiKlinike(klinika.getAdmini()).build();

            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            if (session.isOpen()) {
                session.beginTransaction();
                session.save(temp);

                session.getTransaction().commit();
                session.close();

                return new ResponseEntity<>("Instance created", HttpStatus.CREATED);
            } else {
                System.out.println("Couldn't open connection with database");
                return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<>("Klinika sa tim nazivom vec postoji!", HttpStatus.FORBIDDEN);
    }

}
