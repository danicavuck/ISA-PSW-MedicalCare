package com.groupfour.MedicalCare.Controllers;

import com.groupfour.MedicalCare.Model.DTO.AdminKlinikeDTO;
import com.groupfour.MedicalCare.Service.AdminKlinikeService;
import com.groupfour.MedicalCare.Utill.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@CrossOrigin(allowCredentials = "true")
@RequestMapping("/adminklinike")
public class AdminKlinikeController {
    private Authorization authorization;
    private String[] role = {"adminklinike"};

    @Autowired
    public AdminKlinikeController(Authorization authorization) {
        this.authorization = authorization;
    }

    @PutMapping
    public ResponseEntity<?> azuriranjeLicnihPodataka(@RequestBody AdminKlinikeDTO adminKlinikeDTO,
                                                      HttpSession session){
        if(authorization.hasPermisson(session, role))
        {
            return AdminKlinikeService.azurirajPodatkeAdmina(adminKlinikeDTO, session);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/detalji")
    public ResponseEntity<?> dobavljanjeDetaljaOAdminu(HttpSession session){
        if(authorization.hasPermisson(session, role))
        {
            return AdminKlinikeService.detaljiOAdminu(session);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
}
