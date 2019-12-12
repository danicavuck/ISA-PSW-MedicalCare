package com.groupfour.MedicalCare.Controllers;

import com.groupfour.MedicalCare.Model.DTO.SalaPretragaDTO;
import com.groupfour.MedicalCare.Service.SalaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@CrossOrigin
@RequestMapping("/sale")
public class SalaController {

    public SalaController(){}

    @GetMapping
    public ResponseEntity<ArrayList<SalaPretragaDTO>> getSale(){
        ArrayList<SalaPretragaDTO> sale =  SalaService.getSale();
        if(sale == null){
            System.out.println("Sale nisu nadjene");
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        return new ResponseEntity<>(sale, HttpStatus.OK);
    }
}
