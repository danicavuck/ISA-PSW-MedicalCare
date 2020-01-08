package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.DTO.PacijentDTO;
import com.groupfour.MedicalCare.Model.DTO.UserRole;
import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;
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

    public static ResponseEntity<String> registerPacijent(@RequestBody PacijentDTO pacijentDTO) {
        if (EmailUniqueness.isEmailUniqe(pacijentDTO.getEmail())) {
            return napraviNoviNalogPacijentu(pacijentDTO);
        }
        return new ResponseEntity<>("Email address already taken", HttpStatus.FORBIDDEN);
    }

    public static ResponseEntity<String> napraviNoviNalogPacijentu(PacijentDTO pacijentDTO) {
        Pacijent pacijent = napraviNovogPacijenta(pacijentDTO);
        UserRole userRole = UserRole.builder().user_email(pacijentDTO.getEmail()).role("pacijent").build();
        if (sacuvajUBazuPacijentaIRolu(pacijent, userRole)) {
            return new ResponseEntity<>("Instance created", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Internal server eror", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static Pacijent napraviNovogPacijenta(PacijentDTO pacijentDTO) {
        return Pacijent.builder().email(pacijentDTO.getEmail()).lozinka(PasswordCheck.hash(pacijentDTO.getLozinka())).ime(pacijentDTO.getIme()).prezime(pacijentDTO.getPrezime()).adresa(pacijentDTO.getAdresa()).grad(pacijentDTO.getGrad()).drzava(pacijentDTO.getDrzava()).brojTelefona(pacijentDTO.getBrojTelefona()).brojOsiguranja(pacijentDTO.getBrojOsiguranja()).build();

    }

    public static boolean sacuvajUBazuPacijentaIRolu(Pacijent pacijent, UserRole userRole) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        if (session.isOpen()) {
            session.beginTransaction();
            session.save(userRole);
            session.save(pacijent);
            session.getTransaction().commit();
            session.close();
            return true;
        }

        return false;
    }
}
