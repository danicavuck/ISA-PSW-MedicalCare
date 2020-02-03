package com.groupfour.MedicalCare.Controllers;

import com.groupfour.MedicalCare.Model.DTO.TipPregledaDTO;
import com.groupfour.MedicalCare.Model.Pregled.TipPregleda;
import com.groupfour.MedicalCare.Service.TipPregledaService;
import com.groupfour.MedicalCare.Utill.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
@CrossOrigin(allowCredentials = "true")
@RequestMapping("/tippregleda")
public class TipPregledaController {
    private Authorization authorization;
    private String[] roles = {"adminklinike"};

    @Autowired
    public TipPregledaController(Authorization authorization) {
        this.authorization = authorization;
    }

    @GetMapping
    public ResponseEntity<?> getTipPregleda(HttpSession session) {
        if(authorization.hasPermisson(session, new String[] {"adminklinike", "lekar"}))
        {
            return TipPregledaService.vratiTipovePregleda(session);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping
    public ResponseEntity<?> addTipPregleda(@RequestBody TipPregledaDTO tipPregledaDTO, HttpSession session) {
        if(authorization.hasPermisson(session, roles))
        {
            return TipPregledaService.dodajNoviTipPregleda(tipPregledaDTO, session);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @PutMapping
    public ResponseEntity<?> azurirajTipPregleda(@RequestBody TipPregledaDTO tipPregledaDTO, HttpSession session){
        if(authorization.hasPermisson(session, roles))
        {
            return TipPregledaService.azurirajTipPregleda(tipPregledaDTO);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/brisanje")
    public ResponseEntity<?> obrisiTipPregleda(@RequestBody TipPregledaDTO tipPregledaDTO, HttpSession session){
        if(authorization.hasPermisson(session, roles))
        {
            return TipPregledaService.obrisiTipPregleda(tipPregledaDTO);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
}
