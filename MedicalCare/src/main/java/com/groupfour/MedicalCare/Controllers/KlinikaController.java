package com.groupfour.MedicalCare.Controllers;

import com.groupfour.MedicalCare.Model.DTO.KlinikaDTO;
import com.groupfour.MedicalCare.Service.KlinikaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
@RequestMapping("/klinika")
public class KlinikaController {
    public  KlinikaController(){

    }

    @PutMapping
    public ResponseEntity<String> updateKlinika(@RequestBody KlinikaDTO klinikaDTO){
        if(KlinikaService.updateKlinika(klinikaDTO))
            return new ResponseEntity<>("Uspesna modifikacija klinike", HttpStatus.OK);
        else{
            return new ResponseEntity<>("Neuspesna modifikacija klinike", HttpStatus.BAD_REQUEST);
        }

    }
}
