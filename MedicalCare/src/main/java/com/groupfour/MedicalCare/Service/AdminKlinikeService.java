package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.Administrator.AdminKlinike;
import com.groupfour.MedicalCare.Model.DTO.AdminKlinikeDTO;
import com.groupfour.MedicalCare.Repository.AdminKlinikeRepository;
import com.groupfour.MedicalCare.Utill.PasswordCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class AdminKlinikeService {
    private static AdminKlinikeRepository adminKlinikeRepository;
    private static Logger logger = LoggerFactory.getLogger(AdminKlinikeService.class);

    @Autowired
    public AdminKlinikeService(AdminKlinikeRepository adminKRepository){
        adminKlinikeRepository = adminKRepository;
    }

    public static ResponseEntity<?> azurirajPodatkeAdmina(AdminKlinikeDTO adminKlinikeDTO, HttpSession session){
        AdminKlinike adminKlinike = pronadjiAdminaKlinike(session);
        if(adminKlinike != null)
        {
            izmeniPodatke(adminKlinike, adminKlinikeDTO);
            adminKlinikeRepository.save(adminKlinike);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public static AdminKlinike pronadjiAdminaKlinike(HttpSession session){
        AdminKlinike adminKlinike = adminKlinikeRepository.findAdminKlinikeById((int) session.getAttribute("id"));
        if(adminKlinike != null){
            logger.info("Pronadjen je admin klinike");
            return adminKlinike;
        }
        logger.error("Admin klinike nije pronadjen!");
        return null;
    }

    public static void izmeniPodatke(AdminKlinike adminKlinike, AdminKlinikeDTO adminKlinikeDTO){
        if(!adminKlinikeDTO.getEmail().equals(""))
            adminKlinike.setEmail(adminKlinikeDTO.getEmail());
        if(!adminKlinikeDTO.getIme().equals(""))
            adminKlinike.setIme(adminKlinikeDTO.getIme());
        if(!adminKlinikeDTO.getPrezime().equals(""))
            adminKlinike.setPrezime(adminKlinikeDTO.getPrezime());
        if(trebaIzmenitiLozinku(adminKlinike, adminKlinikeDTO))
            adminKlinike.setLozinka(PasswordCheck.hash(adminKlinikeDTO.getNovaLozinka()));
    }

    public static boolean trebaIzmenitiLozinku(AdminKlinike adminKlinike, AdminKlinikeDTO adminKlinikeDTO){
        boolean zadovoljavajuce = false;
        if(!adminKlinikeDTO.getNovaLozinka().equals("") && PasswordCheck.verifyHash(adminKlinikeDTO.getStaraLozinka() ,
                adminKlinike.getLozinka()))
        {
            zadovoljavajuce = true;
        }
        return zadovoljavajuce;
    }
}
