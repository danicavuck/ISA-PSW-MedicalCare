package com.groupfour.MedicalCare.Controllers;


import com.groupfour.MedicalCare.Model.DTO.LoginDTO;
import com.groupfour.MedicalCare.Model.DTO.UserRole;
import com.groupfour.MedicalCare.Service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping("/login")
public class LoginController {

    public LoginController() {

    }

    @PostMapping
    public ResponseEntity<UserRole> login(@RequestBody LoginDTO loginDTO, HttpServletRequest request) {
        return LoginService.loginPacijent(loginDTO, request);
    }
}
