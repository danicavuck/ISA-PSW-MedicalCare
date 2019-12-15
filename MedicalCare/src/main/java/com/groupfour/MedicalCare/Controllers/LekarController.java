package com.groupfour.MedicalCare.Controllers;

import com.groupfour.MedicalCare.Model.DTO.LekarDTO;
import com.groupfour.MedicalCare.Service.LekarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@CrossOrigin
@RequestMapping("/lekari")
public class LekarController {
    public LekarController(){

    }

    @GetMapping
    public ResponseEntity<ArrayList<LekarDTO>> getLekari(){
        return new ResponseEntity<>(LekarService.getLekareDTO(0), HttpStatus.OK);
    }

    @GetMapping(value = "/{klinikaId}")
    public ResponseEntity<ArrayList<LekarDTO>> getLekariZaKliniku(@PathVariable(value = "klinikaId") Integer klinikaId){
        return new ResponseEntity<>(LekarService.getLekareDTO(klinikaId), HttpStatus.OK);
    }
}
