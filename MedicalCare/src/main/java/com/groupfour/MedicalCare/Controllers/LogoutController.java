package com.groupfour.MedicalCare.Controllers;

import com.groupfour.MedicalCare.Service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
@CrossOrigin(allowCredentials = "true")
@RequestMapping("/odjava")
public class LogoutController {
    public LogoutController() {

    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> logout(HttpSession session){
        return LoginService.logoutPacijent(session);
    }
}
