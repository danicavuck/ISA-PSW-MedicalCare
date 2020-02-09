package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.Zahtevi.RegistracijaPacijenta;
import com.groupfour.MedicalCare.Repository.PacijentRepository;
import com.groupfour.MedicalCare.Utill.EmailUniqueness;
import com.groupfour.MedicalCare.Utill.HibernateUtil;
import com.groupfour.MedicalCare.Utill.PasswordCheck;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


@Service
public class RegisterService {
    private static PacijentRepository pacijentRepository;

    @Autowired
    public RegisterService(PacijentRepository pRepo) {
        pacijentRepository = pRepo;
    }
    public static ResponseEntity<String> registerPacijent(@RequestBody RegistracijaPacijenta registracijaPacijenta) {
        if (EmailUniqueness.isEmailUniqe(registracijaPacijenta.getEmail())) {

            return napraviZahtevZaRegistraciju(registracijaPacijenta);
        }
        return new ResponseEntity<>("Email address already taken", HttpStatus.FORBIDDEN);
    }

    public static ResponseEntity<String> napraviZahtevZaRegistraciju(RegistracijaPacijenta registracijaPacijenta){
        RegistracijaPacijenta reg = napraviNovuRegistraciju(registracijaPacijenta);
        if (sacuvajUBazuRegistraciju(reg)) {
            return new ResponseEntity<>("Instance created", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Internal server eror", HttpStatus.INTERNAL_SERVER_ERROR);
    }



    public static RegistracijaPacijenta napraviNovuRegistraciju(RegistracijaPacijenta registracijaPacijenta){
        return RegistracijaPacijenta.builder().brojTelefona(registracijaPacijenta.getBrojTelefona()).adresa(registracijaPacijenta.getAdresa()).email(registracijaPacijenta.getEmail()).lozinka(PasswordCheck.hash(registracijaPacijenta.getLozinka())).grad(registracijaPacijenta.getGrad()).drzava(registracijaPacijenta.getDrzava()).prezime(registracijaPacijenta.getPrezime()).ime(registracijaPacijenta.getIme()).brojOsiguranja(registracijaPacijenta.getBrojOsiguranja()).odobren(registracijaPacijenta.isOdobren()).aktivan(registracijaPacijenta.isAktivan()).build();
    }



    public static boolean sacuvajUBazuRegistraciju(RegistracijaPacijenta registracijaPacijenta) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        if (session.isOpen()) {
            session.beginTransaction();
            session.save(registracijaPacijenta);
            session.getTransaction().commit();
            session.close();
            return true;
        }

        return false;
    }


}
