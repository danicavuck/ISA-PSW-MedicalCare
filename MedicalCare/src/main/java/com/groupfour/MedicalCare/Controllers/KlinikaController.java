package com.groupfour.MedicalCare.Controllers;


import com.groupfour.MedicalCare.Model.DTO.KlinikaDTO;
import com.groupfour.MedicalCare.Model.DTO.LekarDTO;
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
    private String[] roles = {"adminklinike"};
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


    @GetMapping("/lekari")
    public ResponseEntity<?> getLekari() {
        List<LekarDTO> lekari = KlinikaService.getLekari();
        return new ResponseEntity<>(lekari, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateKlinika(@RequestBody KlinikaDTO klinikaDTO, HttpSession session) {
        if(authorization.hasPermisson(session, roles))
        {
            return KlinikaService.updateKlinika(klinikaDTO);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
}
