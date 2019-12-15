package com.groupfour.MedicalCare.Controllers;


import com.groupfour.MedicalCare.Model.Administrator.AdminKlinike;
import com.groupfour.MedicalCare.Model.DTO.KlinikaDTO;
import com.groupfour.MedicalCare.Model.DTO.LekarDTO;
import com.groupfour.MedicalCare.Model.Klinika.Klinika;
import com.groupfour.MedicalCare.Model.Klinika.Sala;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Model.Osoblje.MedicinskaSestra;
import com.groupfour.MedicalCare.Service.KlinikaService;
import com.groupfour.MedicalCare.Service.LekarService;
import com.sun.mail.iap.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/klinika")
public class KlinikaController {

    @Autowired
    private KlinikaService klinikaService;

    @GetMapping("/klinike")
    public ResponseEntity<?> getKlinike(){
        List<Klinika> klinike = klinikaService.getKlinike();

        return new ResponseEntity<>(klinike,HttpStatus.OK);
    }


    @GetMapping("/lekari")
    public ResponseEntity<?> getLekari(){
        List<LekarDTO> lekari = klinikaService.getLekari();

        return new ResponseEntity<>(lekari, HttpStatus.OK);
    }


    @GetMapping("/medsestre")
    public ResponseEntity<?> getMedicinskeSestre(){
        List<MedicinskaSestra> sestre = klinikaService.getMedicinskeSestre();

        return new ResponseEntity<>(sestre,HttpStatus.OK);
    }

    @GetMapping("/admini")
    public ResponseEntity<?> getAdmini(){
        List<AdminKlinike> admini = klinikaService.getAdminiKlinike();

        return new ResponseEntity<>(admini,HttpStatus.OK);
    }

    @GetMapping("/sale")
    public  ResponseEntity<?> getSale(){
        List<Sala> sale = klinikaService.getSale();

        return new ResponseEntity<>(sale,HttpStatus.OK);
    }


}
