package com.groupfour.MedicalCare.Controllers;

import com.groupfour.MedicalCare.Model.DTO.PregledNaCekanjuDTO;
import com.groupfour.MedicalCare.Service.PreglediNaCekanjuService;
import com.groupfour.MedicalCare.Utill.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@CrossOrigin(allowCredentials = "true")
@RequestMapping("/pregledinacekanju")
public class PreglediNaCekanjuController {
    private Authorization authorization;
    private final String[] roles = {"adminklinike"};

    @Autowired
    public PreglediNaCekanjuController(Authorization authorization){
        this.authorization = authorization;
    }

    @GetMapping
    public ResponseEntity<?> sviPreglediNaCekanjuZaKliniku(HttpSession session) {
        if(authorization.hasPermisson(session, roles))
        {
            return PreglediNaCekanjuService.sviPreglediNaCekanjuZaKliniku(session);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping
    public ResponseEntity<?> odabirSaleZaPregeld(@RequestBody PregledNaCekanjuDTO pregledNaCekanjuDTO,
                                                        HttpSession session) {
        if(authorization.hasPermisson(session, roles))
        {
            return PreglediNaCekanjuService.odabirSaleZaPregled(pregledNaCekanjuDTO, session);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
}
