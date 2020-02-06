package com.groupfour.MedicalCare.Controllers;


import com.groupfour.MedicalCare.Model.DTO.*;
import com.groupfour.MedicalCare.Service.KlinikaService;
import com.groupfour.MedicalCare.Utill.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@CrossOrigin(allowCredentials = "true")
@RequestMapping("/klinika")
public class KlinikaController {
    private Authorization authorization;
    private String[] roles = {"adminklinike","adminkc"};
    @Autowired
    public KlinikaController(Authorization authorization) {
        this.authorization = authorization;
    }

    @GetMapping("/klinike")
    public ResponseEntity<?> getKlinike(HttpSession session) {
        if(authorization.hasPermisson(session, roles))
        {
            ArrayList<KlinikaDTO> klinike = KlinikaService.getKlinike(session);
            return new ResponseEntity<>(klinike, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/klinikeSve")
    public ResponseEntity<?> getKlinikeSve(HttpSession session) {

        if(authorization.hasPermisson(session,roles)) {
            ArrayList<KlinikaSveDTO> klinike = KlinikaService.getKlinikeSve(session);
            return new ResponseEntity<>(klinike, HttpStatus.OK);
        }

        return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/lekari")
    public ResponseEntity<?> getLekari(HttpSession session) {

        if(authorization.hasPermisson(session,roles)) {
            List<LekarDTO> lekari = KlinikaService.getLekari(session);
            return new ResponseEntity<>(lekari, HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/medsestre")
    public ResponseEntity<?> getSestre(HttpSession session) {

        if(authorization.hasPermisson(session,roles)) {
            List<MedSestraSveDTO> sestre = KlinikaService.getSestre(session);
            return new ResponseEntity<>(sestre, HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/admini")
    public ResponseEntity<?> getAdminiKlinike(HttpSession session) {

        if(authorization.hasPermisson(session,roles)) {
            List<AdminKlinikeSveDTO> admini = KlinikaService.getAdminiKlinike(session);
            return new ResponseEntity<>(admini, HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
    }


    @GetMapping("/sale")
    public ResponseEntity<?> getSale(HttpSession session) {

        if(authorization.hasPermisson(session,roles)) {
            List<SalaSveDTO> sale = KlinikaService.getSale(session);
            return new ResponseEntity<>(sale, HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
    }



    @PutMapping
    public ResponseEntity<?> updateKlinika(@RequestBody KlinikaDTO klinikaDTO, HttpSession session) {
        if(authorization.hasPermisson(session, roles))
        {
            return KlinikaService.updateKlinika(klinikaDTO);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/prihodi")
    public ResponseEntity<?> prihodiKlinikeZaOdredjeniPeriod(@RequestBody PrihodDTO prihodDTO, HttpSession session)
    {
        if(authorization.hasPermisson(session, new String[] {"adminklinike"}))
        {
            return KlinikaService.prihodiKlinike(prihodDTO, session);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

}
