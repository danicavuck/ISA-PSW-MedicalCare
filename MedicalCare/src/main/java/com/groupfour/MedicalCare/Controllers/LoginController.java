package com.groupfour.MedicalCare.Controllers;


import com.groupfour.MedicalCare.Model.DTO.LoginDTO;
import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;
import com.groupfour.MedicalCare.Repository.PacijentRepository;
import com.groupfour.MedicalCare.Utill.PasswordCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
@RequestMapping("/login")
public class LoginController {

    private PacijentRepository pacijentRepository;

    @Autowired
    public LoginController(PacijentRepository pacijentRepository){
        this.pacijentRepository = pacijentRepository;
    }

    @PostMapping
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO){

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
