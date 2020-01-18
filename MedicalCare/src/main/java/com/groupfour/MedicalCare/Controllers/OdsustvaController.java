package com.groupfour.MedicalCare.Controllers;

import com.groupfour.MedicalCare.Model.DTO.OdsustvaZaAdminaDTO;
import com.groupfour.MedicalCare.Model.DTO.OdsustvoDTO;
import com.groupfour.MedicalCare.Service.OdsustvaService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(allowCredentials = "true")
@RequestMapping("/odsustva")
public class OdsustvaController {
    public OdsustvaController(){}

    @PostMapping
    public static ResponseEntity<?> dodajNoviZahtevZaOdsustvoLekara(@RequestBody OdsustvoDTO odsustvoDTO) {
        return OdsustvaService.dodajNoviZahtevZaOdsustvoLekara(odsustvoDTO);
    }

    @GetMapping(value = {"/{klinikaId}"})
    public static ResponseEntity<?> vratiZahteveZaOdsustvoLekaraZaKliniku(@PathVariable(value = "klinikaId") Integer klinikaId) {
        return OdsustvaService.vratiZahteveZaOdsustvoLekaraZaKliniku(klinikaId);
    }

    // Jasno je da nije po konvenciji, ali Angular pravi problem kod tela DELETE zahteva, zato koristim POST
    @PostMapping(value = {"/brisanje/{klinikaId}"})
    public static ResponseEntity<?> obrisiZahtevZaOdsustvoLekaraZaKliniku(@PathVariable (value = "klinikaId") Integer klinikaId, @RequestBody OdsustvaZaAdminaDTO odsustvaZaAdminaDTO) {
        return OdsustvaService.obrisiZahtevZaOdsustvoLekaraZaKliniku(klinikaId, odsustvaZaAdminaDTO);
    }
}
