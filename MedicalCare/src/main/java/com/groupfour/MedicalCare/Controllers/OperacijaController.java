package com.groupfour.MedicalCare.Controllers;

import com.groupfour.MedicalCare.Model.DTO.DodavanjeSaleDTO;
import com.groupfour.MedicalCare.Model.DTO.OperacijaDTO;
import com.groupfour.MedicalCare.Service.OperacijaService;
import com.groupfour.MedicalCare.Utill.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@CrossOrigin(allowCredentials = "true")
@RequestMapping("/operacije")
public class OperacijaController {
    private Authorization authorization;
    private String[] role = {"lekar"};

    @Autowired
    public OperacijaController(Authorization authorization){
        this.authorization = authorization;
    }

    @GetMapping
    public ResponseEntity<?> vratiOperacijeZaOdgovarajucuKliniku(HttpSession session){
        if(authorization.hasPermisson(session, new String[] {"adminklinike"}))
        {
            return OperacijaService.vratiOperacijeNaCekanjuZaOdgovarajucuKliniku(session);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping
    public ResponseEntity<?> dodavanjeOperacije(@RequestBody OperacijaDTO operacijaDTO, HttpSession session){
        if(authorization.hasPermisson(session, role))
        {
            return OperacijaService.dodajOperaciju(operacijaDTO, session);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @PutMapping
    public ResponseEntity<?> dodavanjeSaleOperaciji(@RequestBody DodavanjeSaleDTO dodavanjeSaleDTO, HttpSession session){
        if(authorization.hasPermisson(session, new String[] {"adminklinike"}))
        {
            return OperacijaService.azurirajOperaciju(dodavanjeSaleDTO, session);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
}
