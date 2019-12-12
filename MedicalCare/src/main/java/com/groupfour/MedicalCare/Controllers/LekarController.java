package com.groupfour.MedicalCare.Controllers;

import com.groupfour.MedicalCare.Model.DTO.LekarDTO;
import com.groupfour.MedicalCare.Service.LekarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@CrossOrigin
@RequestMapping("/lekari")
public class LekarController {
    public LekarController(){

    }

    @GetMapping
    public ResponseEntity<ArrayList<LekarDTO>> getLekari(){
        return new ResponseEntity<>(LekarService.getLekareDTO(), HttpStatus.OK);
    }
}
