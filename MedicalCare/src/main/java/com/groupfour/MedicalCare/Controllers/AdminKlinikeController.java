package com.groupfour.MedicalCare.Controllers;

import com.groupfour.MedicalCare.Model.DTO.KlinikaDTO;
import com.groupfour.MedicalCare.Service.KlinikaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@CrossOrigin(allowCredentials = "true")
@RequestMapping("/adminklinike")
public class AdminKlinikeController {

    @GetMapping("/klinike")
    public ResponseEntity<List<KlinikaDTO>> getKlinike(HttpSession session) {
        ArrayList<KlinikaDTO> klinike = KlinikaService.getKlinike(session);
        return new ResponseEntity<>(klinike, HttpStatus.OK);
    }
}
