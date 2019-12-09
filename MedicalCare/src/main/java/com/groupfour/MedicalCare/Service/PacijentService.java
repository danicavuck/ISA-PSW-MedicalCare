package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.DTO.LoginDTO;
import com.groupfour.MedicalCare.Model.DTO.PacijentDTO;
import com.groupfour.MedicalCare.Model.HibernateUtil;
import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;
import com.groupfour.MedicalCare.Repository.PacijentRepository;
import com.groupfour.MedicalCare.Utill.PasswordCheck;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class PacijentService {

    private static PacijentRepository pacijentRepository;

    @Autowired
    public PacijentService(PacijentRepository pRepository){
        pacijentRepository = pRepository;
    }

    public static ResponseEntity<String> loginPacijent(@RequestBody LoginDTO loginDTO){
        Pacijent pacijent = pacijentRepository.findUserByEmail(loginDTO.getEmail());
        if(pacijent == null){
            return new ResponseEntity<>("Not authorized", HttpStatus.FORBIDDEN);
        }

        if(!PasswordCheck.verifyHash(loginDTO.getLozinka(), pacijent.getLozinka())){
            return new ResponseEntity<>("Incorrect credentials", HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    public static ResponseEntity<String> registerPacijent(@RequestBody PacijentDTO pacijentDTO){
        Pacijent pacijent = pacijentRepository.findUserByEmail(pacijentDTO.getEmail());
        if(pacijent == null){
            pacijent = Pacijent.builder().email(pacijentDTO.getEmail()).lozinka(PasswordCheck.hash(pacijentDTO.getLozinka())).ime(pacijentDTO.getIme()).prezime(pacijentDTO.getPrezime()).adresa(pacijentDTO.getAdresaPrebivalista()).grad(pacijentDTO.getGrad()).drzava(pacijentDTO.getDrzava()).brojTelefona(pacijentDTO.getTelefon()).brojOsiguranja(pacijentDTO.getBrojOsiguranja()).build();


            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            if(session.isOpen()){
                session.beginTransaction();
                //session.save(adminKC);
                session.save(pacijent);

                session.getTransaction().commit();
                session.close();

                return new ResponseEntity<>("Instance created", HttpStatus.CREATED);
            } else{
                System.out.println("Couldn't open connection with database");
                return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<>("Email address already taken", HttpStatus.FORBIDDEN);
    }
}
