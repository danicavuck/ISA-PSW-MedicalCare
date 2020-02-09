package com.groupfour.MedicalCare.Controllers;


import com.groupfour.MedicalCare.Model.DTO.LoginDTO;
import com.groupfour.MedicalCare.Model.DTO.PromenaLozinkeDTO;
import com.groupfour.MedicalCare.Model.DTO.UserRole;
import com.groupfour.MedicalCare.Service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@CrossOrigin(allowCredentials = "true")
@RequestMapping("/login")
public class LoginController {

    public LoginController() {
    }

    @PostMapping
    public ResponseEntity<UserRole> login(@RequestBody LoginDTO loginDTO, HttpSession session) {
        System.out.println("->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        return LoginService.loginPacijent(loginDTO, session);
    }

    @PutMapping
    public ResponseEntity<?> promenaLozinke(@RequestBody PromenaLozinkeDTO promenaLozinkeDTO, HttpSession session){
        return LoginService.promenaLozinke(promenaLozinkeDTO, session);
    }
}
