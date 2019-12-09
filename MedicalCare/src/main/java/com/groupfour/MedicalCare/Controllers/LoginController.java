package com.groupfour.MedicalCare.Controllers;


import com.groupfour.MedicalCare.Model.DTO.LoginDTO;
import com.groupfour.MedicalCare.Service.PacijentService;
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

    public LoginController(){

    }

    @PostMapping
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO){
        return PacijentService.loginPacijent(loginDTO);
    }
}
