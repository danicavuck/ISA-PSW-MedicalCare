package com.groupfour.MedicalCare.Controllers;

import com.groupfour.MedicalCare.Model.DTO.OdsustvoDTO;
import com.groupfour.MedicalCare.Model.DTO.ReceptDTO;
import com.groupfour.MedicalCare.Model.Dokumenti.Recept;
import com.groupfour.MedicalCare.Service.MedicinskaSestraService;
import com.groupfour.MedicalCare.Service.OdsustvaService;
import com.groupfour.MedicalCare.Service.ReceptService;
import com.groupfour.MedicalCare.Utill.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@CrossOrigin(allowCredentials = "true")
@RequestMapping("/medsestra")
public class MedicinskaSestraController {

    @Autowired
    ReceptService receptService;
    private Authorization authorization;
    private String[] role = {"med_sestra"};

    @Autowired
    public MedicinskaSestraController(Authorization authorization){this.authorization = authorization;}

    @RequestMapping(value = "/recepti", method = RequestMethod.GET)
    public ResponseEntity<?> getRecepti() {

        List<ReceptDTO> temp = receptService.getAllActive();

        return new ResponseEntity<>(temp, HttpStatus.OK);
    }


    @RequestMapping(value = "/overiRecept", method = RequestMethod.PUT)
    public ResponseEntity<?> overiRecept(@RequestBody Integer id, HttpSession session) {

        if(authorization.hasPermisson(session,role)) {
            receptService.overiRecept(id,session);
            System.out.println("overen");
            return new ResponseEntity<String>("Recept je overen!", HttpStatus.OK);

        }else{
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/dodajOdsustvo",method = RequestMethod.POST)
    public ResponseEntity<?> dodajZahtevZaOdsustvo(@RequestBody OdsustvoDTO odsustvoDTO, HttpSession session){
        if(authorization.hasPermisson(session,role)){
            return MedicinskaSestraService.dodajNoviZahtevZaOdsustvoMedicinskeSestre(odsustvoDTO,session);
        }

        return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
    }



}