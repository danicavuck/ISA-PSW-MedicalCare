package com.groupfour.MedicalCare.Controllers;

import com.groupfour.MedicalCare.Model.DTO.PregledNaCekanjuDTO;
import com.groupfour.MedicalCare.Service.PreglediNaCekanjuService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(allowCredentials = "true")
@RequestMapping("/pregledinacekanju")
public class PreglediNaCekanjuController {
    public PreglediNaCekanjuController(){}

    @GetMapping(value = "{klinikaId}")
    public static ResponseEntity<?> sviPreglediNaCekanjuZaKliniku(@PathVariable(value = "klinikaId") Integer klinikaId) {
        return PreglediNaCekanjuService.sviPreglediNaCekanjuZaKliniku(klinikaId);
    }

    @PostMapping
    public static ResponseEntity<?> odabirSaleZaPregeld(@RequestBody PregledNaCekanjuDTO pregledNaCekanjuDTO) {
        return PreglediNaCekanjuService.odabirSaleZaPregled(pregledNaCekanjuDTO);
    }
}
