package com.groupfour.MedicalCare.Controllers;

import com.groupfour.MedicalCare.Model.DTO.PregledDTO;
import com.groupfour.MedicalCare.Service.PregledService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@CrossOrigin
@RequestMapping("/pregledi")
public class PregledController {
    public PregledController() {

    }

    @GetMapping
    public static ResponseEntity<ArrayList<PregledDTO>> dobaviSvePreglede() {
        ArrayList<PregledDTO> pregledi = PregledService.dobaviSvePreglede();
        return new ResponseEntity<>(pregledi, HttpStatus.OK);
    }

    @GetMapping(value = "/{klinikaId}")
    public static ResponseEntity<ArrayList<PregledDTO>> dobaviPregledeZaKliniku(@PathVariable(value = "klinikaId") Integer klinikaId) {
        ArrayList<PregledDTO> pregledi = PregledService.dobaviPregledeZaKliniku(klinikaId);
        return new ResponseEntity<>(pregledi, HttpStatus.OK);
    }

    @PostMapping
    public static ResponseEntity<String> kreirajNoviPregled(@RequestBody PregledDTO pregledDTO) {
        PregledService.kreirajNoviPregled(pregledDTO);
        return new ResponseEntity<>(pregledDTO.toString(), HttpStatus.CREATED);
    }
}
