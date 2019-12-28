package com.groupfour.MedicalCare.Controllers;

import com.groupfour.MedicalCare.Service.PacijentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
@RequestMapping("/pacijenti")
public class PacijentController {
    public PacijentController(){}

    @GetMapping(value = "/{klinikaId}")
    public ResponseEntity<?> dobaviPacijenteOdgovarajuceKlinike(@PathVariable(value = "klinikaId") Integer klinikaId){
        return PacijentService.dobaviPacijenteOdgovarajuceKlinike(klinikaId);
    }
}
