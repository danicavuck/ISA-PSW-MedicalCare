package com.groupfour.MedicalCare.Controllers;

import com.groupfour.MedicalCare.Model.DTO.IzvestajIzmenaDTO;
import com.groupfour.MedicalCare.Model.DTO.IzvestajOPregleduDTO;
import com.groupfour.MedicalCare.Model.Dokumenti.IzvestajOPregledu;
import com.groupfour.MedicalCare.Service.IzvestajOPregleduService;
import com.groupfour.MedicalCare.Utill.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@CrossOrigin(allowCredentials = "true")
@RequestMapping("/izvestaj")
public class IzvestajOPregleduController {
    private Authorization authorization;
    private String[] roles = {"lekar"};
    private static IzvestajOPregleduService izvestajOPregleduService;

    @Autowired
    public IzvestajOPregleduController(Authorization authorization,IzvestajOPregleduService iService){
        this.authorization = authorization;
        izvestajOPregleduService = iService;
    }

    @RequestMapping(value = "/dodajIzvestaj", method = RequestMethod.POST)
    public ResponseEntity<?> dodavanjeIzvestaja(@RequestBody IzvestajOPregleduDTO izvestajOPregleduDTO, HttpSession session){
        if(authorization.hasPermisson(session, roles))
        {

            return izvestajOPregleduService.dodajIzvestajOPregledu(izvestajOPregleduDTO,session);
        }
        return new ResponseEntity<>("null", HttpStatus.UNAUTHORIZED);
    }

    @PutMapping
    public ResponseEntity<?> azurirajPodatkeSale(@RequestBody IzvestajIzmenaDTO izvestajIzmenaDTO, HttpSession session){
        if(authorization.hasPermisson(session, roles))
        {
            return IzvestajOPregleduService.azurirajIzvestaj(izvestajIzmenaDTO, session);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping
    public ResponseEntity<?> getIzvestaj(HttpSession session) {
        if(authorization.hasPermisson(session, roles))
        {

            return IzvestajOPregleduService.dobaviSveIzvestajeZaLekara(session);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }


}
