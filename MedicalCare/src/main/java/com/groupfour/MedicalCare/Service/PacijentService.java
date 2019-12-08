package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.DTO.LoginDTO;
import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;
import com.groupfour.MedicalCare.Repository.PacijentRepository;
import com.groupfour.MedicalCare.Utill.PasswordCheck;
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
}
