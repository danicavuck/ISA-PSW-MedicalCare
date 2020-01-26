package com.groupfour.MedicalCare.Controllers;

import com.groupfour.MedicalCare.Model.DTO.MedSestraIzmenaPodatakaDTO;
import com.groupfour.MedicalCare.Service.MedicinskaSestraService;
import com.groupfour.MedicalCare.Utill.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@CrossOrigin(allowCredentials = "true")
@RequestMapping("/medicinskesestre")
public class MedicinskaSestraController {
    private Authorization authorization;
    private String[] roles = {"med_sestra"};

    @Autowired
    public MedicinskaSestraController(Authorization authorization){
        this.authorization = authorization;
    }

    @PutMapping
    public ResponseEntity<?> azuriranjePodatakaMedicinskeSestre(@RequestBody MedSestraIzmenaPodatakaDTO medSestraIzmenaPodatakaDTO, HttpSession session) {
        if(authorization.hasPermisson(session, roles))
        {
            return MedicinskaSestraService.azurirajPodatkeMedicinskeSestre(medSestraIzmenaPodatakaDTO, session);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
}
