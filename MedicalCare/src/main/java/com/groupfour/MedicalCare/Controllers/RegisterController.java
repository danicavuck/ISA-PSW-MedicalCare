package com.groupfour.MedicalCare.Controllers;

import com.groupfour.MedicalCare.Model.DTO.PacijentDTO;
import com.groupfour.MedicalCare.Model.Zahtevi.RegistracijaPacijenta;
import com.groupfour.MedicalCare.Repository.PacijentRepository;
import com.groupfour.MedicalCare.Service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin(allowCredentials = "true")
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    public RegisterController(PacijentRepository pacijentRepository) {

    }

    @PostMapping
    public ResponseEntity<String> register(@RequestBody RegistracijaPacijenta registracijaPacijenta) {
        return RegisterService.registerPacijent(registracijaPacijenta);
    }
}
