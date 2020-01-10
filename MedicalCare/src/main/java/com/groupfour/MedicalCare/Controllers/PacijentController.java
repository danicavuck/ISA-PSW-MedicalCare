package com.groupfour.MedicalCare.Controllers;

import com.groupfour.MedicalCare.Service.PacijentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@CrossOrigin(allowCredentials = "true")
@RequestMapping("/pacijenti")
public class PacijentController {
    public PacijentController(){}

    @GetMapping(value = "/{klinikaId}")
    public ResponseEntity<?> dobaviPacijenteOdgovarajuceKlinike(@PathVariable(value = "klinikaId") Integer klinikaId,
                                                                HttpSession session){
        if(session.getAttribute("role").equals("lekar")){
            return PacijentService.dobaviPacijenteOdgovarajuceKlinike(klinikaId);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping(value = "/pacijent/{pacijentId}")
    public ResponseEntity<?> dobaviKonkretnogPacijenta(@PathVariable(value = "pacijentId") Integer pacijentId){
        return PacijentService.dobaviKonkretnogPacijenta(pacijentId);
    }

    @PostMapping(value = "/pretraga")
    public ResponseEntity<?> dobaviPacijentaZaImeIPrezime(@RequestBody String imeIPrezime) {
        return PacijentService.dobaviPacijentaZaImeIPrezime(imeIPrezime);
    }
}
