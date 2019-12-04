package com.groupfour.MedicalCare.Controllers;

import com.groupfour.MedicalCare.Model.DTO.PacijentDTO;
import com.groupfour.MedicalCare.Model.HibernateUtil;
import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;
import com.groupfour.MedicalCare.Repository.PacijentRepository;
import com.groupfour.MedicalCare.Utill.PasswordCheck;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class HomeController {
    @Autowired
    PacijentRepository repository;

    @GetMapping("/")
    public static String home() {
        return "index.html";
    }

    @PostMapping("/login")
    public @ResponseBody
    ResponseEntity<String> login(@RequestBody PacijentDTO pacijentDTO) {

        Pacijent pacijent = repository.findUserByEmail(pacijentDTO.getEmail());
        if(pacijent == null){
            return new ResponseEntity<>("Not authorizes", HttpStatus.FORBIDDEN);
        }

        if(!PasswordCheck.verifyHash(pacijentDTO.getLozinka(), pacijent.getLozinka())){
            return new ResponseEntity<>("Not authorizes", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody PacijentDTO pacijentDTO){

        Pacijent pacijent = repository.findUserByEmail(pacijentDTO.getEmail());
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
            }
        }

        return new ResponseEntity<>("Email address already taken", HttpStatus.FORBIDDEN);
    }
}
