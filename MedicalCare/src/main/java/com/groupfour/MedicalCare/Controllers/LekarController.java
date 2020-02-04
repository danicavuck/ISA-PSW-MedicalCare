package com.groupfour.MedicalCare.Controllers;

import com.groupfour.MedicalCare.Model.DTO.DodavanjeLekaraDTO;
import com.groupfour.MedicalCare.Model.DTO.LekarDTO;
import com.groupfour.MedicalCare.Model.DTO.LekarIzmenaPodatakaDTO;
import com.groupfour.MedicalCare.Service.LekarService;
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
@RequestMapping("/lekari")
public class LekarController {
    private Authorization authorization;
    private String[] roles = {"adminklinike"};

    @Autowired
    public LekarController(Authorization authorization) {
        this.authorization = authorization;
    }

    @GetMapping
    public ResponseEntity<?> getLekari(HttpSession session) {
        if(authorization.hasPermisson(session, roles))
        {
            return LekarService.getLekareDTO(session);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping
    public ResponseEntity<?> brisanjeLekara(@RequestBody LekarDTO lekarDTO, HttpSession session){
        if(authorization.hasPermisson(session, roles))
        {
            return LekarService.brisanjeLekara(lekarDTO);
        }
        return new ResponseEntity<>("Brisanje nije dozvoljeno", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping
    public ResponseEntity<?> dodavanjeNovogLekara(@RequestBody DodavanjeLekaraDTO dodavanjeLekaraDTO, HttpSession session){
        if(authorization.hasPermisson(session, roles))
        {
            return LekarService.dodavanjeNovogLekara(dodavanjeLekaraDTO, session);
        }
        return new ResponseEntity<>("null", HttpStatus.UNAUTHORIZED);
    }

    @PutMapping
    public ResponseEntity<?> izmeniPodatkeLekara(@RequestBody LekarIzmenaPodatakaDTO lekarIzmenaPodatakaDTO,
                                                 HttpSession session){
        if(authorization.hasPermisson(session, new String[] {"lekar"}))
        {
            return LekarService.izmenaLicnihPodataka(lekarIzmenaPodatakaDTO, session);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping(value = "/zauzece")
    public ResponseEntity<?> dobaviPregledeIOperacijeZaRadniKalendar(HttpSession session){
        if(authorization.hasPermisson(session, new String[] {"lekar"}))
        {
            return LekarService.preglediIOperacijeZaRadniKalendar(session);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
}
