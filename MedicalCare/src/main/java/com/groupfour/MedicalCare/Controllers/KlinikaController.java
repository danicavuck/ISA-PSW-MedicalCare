package com.groupfour.MedicalCare.Controllers;


import com.groupfour.MedicalCare.Model.DTO.KlinikaDTO;
import com.groupfour.MedicalCare.Model.DTO.LekarDTO;
import com.groupfour.MedicalCare.Service.KlinikaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/klinika")
public class KlinikaController {

    private static KlinikaService klinikaService;

    @Autowired
    public KlinikaController(KlinikaService kService) {
        klinikaService = kService;
    }

    @GetMapping("/klinike")
    public ResponseEntity<?> getKlinike() {
        ArrayList<KlinikaDTO> klinike = KlinikaService.getKlinike();
        return new ResponseEntity<>(klinike, HttpStatus.OK);
    }


    @GetMapping("/lekari")
    public ResponseEntity<?> getLekari() {
        List<LekarDTO> lekari = KlinikaService.getLekari();

        return new ResponseEntity<>(lekari, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<String> updateKlinika(@RequestBody KlinikaDTO klinikaDTO) {
        if (KlinikaService.updateKlinika(klinikaDTO))
            return new ResponseEntity<>("Uspesna modifikacija klinike", HttpStatus.OK);
        else {
            return new ResponseEntity<>("Neuspesna modifikacija klinike", HttpStatus.BAD_REQUEST);
        }

    }
}