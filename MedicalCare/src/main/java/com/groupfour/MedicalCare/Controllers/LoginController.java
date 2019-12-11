package com.groupfour.MedicalCare.Controllers;


import com.groupfour.MedicalCare.Model.DTO.LoginDTO;
import com.groupfour.MedicalCare.Service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@CrossOrigin
@RequestMapping("/login")
public class LoginController {

    public LoginController(){

    }

    @PostMapping
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO, HttpServletRequest request){
        return LoginService.loginPacijent(loginDTO, request);
    }
}
