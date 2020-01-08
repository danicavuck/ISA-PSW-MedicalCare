package com.groupfour.MedicalCare.Controllers;

import com.groupfour.MedicalCare.Service.PreglediNaCekanjuService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
@RequestMapping("/pregledinacekanju")
public class PreglediNaCekanjuController {
    public PreglediNaCekanjuController(){}

    @GetMapping(value = "{klinikaId}")
    public static ResponseEntity<?> sviPreglediNaCekanjuZaKliniku(@PathVariable(value = "klinikaId") Integer klinikaId) {
        return PreglediNaCekanjuService.sviPreglediNaCekanjuZaKliniku(klinikaId);
    }
}
