package com.groupfour.MedicalCare.Controllers;

import com.groupfour.MedicalCare.Model.DTO.OdsustvaZaAdminaDTO;
import com.groupfour.MedicalCare.Model.DTO.OdsustvoDTO;
import com.groupfour.MedicalCare.Service.OdsustvaService;
import com.groupfour.MedicalCare.Utill.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@CrossOrigin(allowCredentials = "true")
@RequestMapping("/odsustva")
public class OdsustvaController {
    private Authorization authorization;
    private String[] role = {"adminklinike"};

    @Autowired
    public OdsustvaController(Authorization authorization){
        this.authorization = authorization;
    }

    @PostMapping
    public ResponseEntity<?> dodajNoviZahtevZaOdsustvoLekara(@RequestBody OdsustvoDTO odsustvoDTO, HttpSession session) {
        if(authorization.hasPermisson(session, new String[] {"lekar"}))
        {
            return OdsustvaService.dodajNoviZahtevZaOdsustvoLekara(odsustvoDTO, session);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping
    public ResponseEntity<?> vratiZahteveZaOdsustvoLekaraZaKliniku(HttpSession session) {
        if(authorization.hasPermisson(session, role))
        {
            return OdsustvaService.vratiZahteveAdminu(session);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    // Jasno je da nije po konvenciji, ali Angular pravi problem kod tela DELETE zahteva, zato koristim POST metodu
    // za brisanje
    @PostMapping(value = "/brisanje")
    public ResponseEntity<?> obrisiZahtevZaOdsustvoLekaraZaKliniku(@RequestBody OdsustvaZaAdminaDTO odsustvaZaAdminaDTO, HttpSession session) {
        return OdsustvaService.obrisiZahtevZaOdsustvoLekaraZaKliniku(odsustvaZaAdminaDTO, session);
    }
}
