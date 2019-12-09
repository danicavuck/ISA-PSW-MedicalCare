package com.groupfour.MedicalCare.Controllers;

import com.groupfour.MedicalCare.Model.DTO.PacijentDTO;
import com.groupfour.MedicalCare.Repository.PacijentRepository;
import com.groupfour.MedicalCare.Service.PacijentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    public RegisterController(PacijentRepository pacijentRepository){

    }

    @PostMapping
    public ResponseEntity<String> register(@RequestBody PacijentDTO pacijentDTO){
        return PacijentService.registerPacijent(pacijentDTO);
    }
}
