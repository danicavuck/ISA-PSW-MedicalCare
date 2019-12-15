package com.groupfour.MedicalCare.Controllers;

import com.groupfour.MedicalCare.Model.DTO.TipPregledaDTO;
import com.groupfour.MedicalCare.Service.TipPregledaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@CrossOrigin
@RequestMapping("/tippregleda")
public class TipPregledaController {
    public TipPregledaController(){

    }

    @GetMapping
    public ResponseEntity<ArrayList<TipPregledaDTO>> getTipPregleda(){
        return new ResponseEntity<>(TipPregledaService.getTipPregleda(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addTipPregleda(@RequestBody TipPregledaDTO tipPregledaDTO){
        TipPregledaService.addTipPregleda(tipPregledaDTO);
        return new ResponseEntity<>("Dodat tip pregleda", HttpStatus.OK);
    }
}
