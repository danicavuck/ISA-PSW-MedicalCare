package com.groupfour.MedicalCare.Controllers;

import com.groupfour.MedicalCare.Service.PacijentService;
import com.groupfour.MedicalCare.Utill.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@CrossOrigin(allowCredentials = "true")
@RequestMapping("/pacijenti")
public class PacijentController {
    private String[] role = {"lekar", "adminklinike", "adminkc", "med_sestra"};
    private Authorization authorization;

    @Autowired
    public PacijentController(Authorization authorization){
        this.authorization = authorization;
    }

    @GetMapping(value = "/{klinikaId}")
    public ResponseEntity<?> dobaviPacijenteOdgovarajuceKlinike(HttpSession session){
        if(authorization.hasPermisson(session, role)){
            return PacijentService.dobaviPacijente(session);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping(value = "/pacijent/{pacijentId}")
    public ResponseEntity<?> dobaviKonkretnogPacijenta(@PathVariable(value = "pacijentId") Integer pacijentId,
                                                       HttpSession session){
        if(authorization.hasPermisson(session, role))
        {
            return PacijentService.dobaviPacijenta(pacijentId);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping(value = "/klinika")
    public ResponseEntity<?> dobaviPacijenteZaKliniku(HttpSession session)
    {
        if(authorization.hasPermisson(session, new String[] {"lekar"}))
        {
            return PacijentService.dobaviPacijenteZaLekara(session);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping(value = "/pretraga")
    public ResponseEntity<?> dobaviPacijentaZaImeIPrezime(@RequestBody String imeIPrezime, HttpSession session) {
        if(authorization.hasPermisson(session, role))
        {
            return PacijentService.dobaviPacijentaZaImeIPrezime(imeIPrezime);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
}
