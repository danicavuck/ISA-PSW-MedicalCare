package com.groupfour.MedicalCare.Controllers;

import com.groupfour.MedicalCare.Model.DTO.SalaDodavanjeDTO;
import com.groupfour.MedicalCare.Model.DTO.SalaPretragaDTO;
import com.groupfour.MedicalCare.Service.SalaService;
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
@RequestMapping("/sale")
public class SalaController {
    private Authorization authorization;
    private String[] roles = {"adminklinike"};

    @Autowired
    public SalaController(Authorization authorization) {
        this.authorization = authorization;
    }

    @GetMapping
    public ResponseEntity<ArrayList<SalaPretragaDTO>> vratiSaleZaOdredjenuKliniku(HttpSession session) {
        if(authorization.hasPermisson(session, new String[] {"adminklinike", "lekar"}))
        {
            ArrayList<SalaPretragaDTO> sale = SalaService.getSale(session);
            return new ResponseEntity<>(sale, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteSala(@RequestBody SalaPretragaDTO salaPretragaDTO, HttpSession session) {
        if(authorization.hasPermisson(session, roles))
        {
            return SalaService.deleteSala(salaPretragaDTO);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping
    public ResponseEntity<?> addSala(@RequestBody SalaDodavanjeDTO salaDodavanjeDTO, HttpSession session) {
        if(authorization.hasPermisson(session, roles))
        {
            SalaService.addSala(salaDodavanjeDTO, session);
            return new ResponseEntity<String>("Uspesno dodavanje sale", HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping(value = "/pretraga")
    public ResponseEntity<?> saleSearch(@RequestBody SalaPretragaDTO salaPretragaDTO, HttpSession session) {
        return SalaService.pretraziSaluPoBrojuSale(salaPretragaDTO);
    }
}
