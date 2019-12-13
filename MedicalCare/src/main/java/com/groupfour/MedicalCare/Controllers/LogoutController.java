package com.groupfour.MedicalCare.Controllers;

import com.groupfour.MedicalCare.Service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
@RequestMapping("/logout")
public class LogoutController {
    public LogoutController(){

    }

    @GetMapping
    public ResponseEntity<String> logout(){
        return LoginService.logoutPacijent();
    }
}
