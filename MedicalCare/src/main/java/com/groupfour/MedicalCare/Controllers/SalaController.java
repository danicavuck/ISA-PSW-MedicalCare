package com.groupfour.MedicalCare.Controllers;

import com.groupfour.MedicalCare.Model.DTO.SalaDodavanjeDTO;
import com.groupfour.MedicalCare.Model.DTO.SalaPretragaDTO;
import com.groupfour.MedicalCare.Service.SalaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@CrossOrigin
@RequestMapping("/sale")
public class SalaController {

    public SalaController(){}
    @GetMapping
    public ResponseEntity<ArrayList<SalaPretragaDTO>> getSaleSve(){
        ArrayList<SalaPretragaDTO> sale =  SalaService.getSale(0);
        if(sale == null){
            System.out.println("Sale nisu nadjene");
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        return new ResponseEntity<>(sale, HttpStatus.OK);
    }

    @GetMapping(value = "/{klinikaId}")
    public ResponseEntity<ArrayList<SalaPretragaDTO>> getSaleZaKliniku(@PathVariable(value = "klinikaId") Integer klinikaId){
        ArrayList<SalaPretragaDTO> sale =  SalaService.getSale(klinikaId);
        if(sale == null){
            System.out.println("Sale nisu nadjene");
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        return new ResponseEntity<>(sale, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteSala(@RequestBody SalaPretragaDTO salaPretragaDTO){
        SalaService.deleteSala(salaPretragaDTO);

        return new ResponseEntity<String>("Uspesno izvrseno brisanje", HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<?> addSala(@RequestBody SalaDodavanjeDTO salaDodavanjeDTO){
        SalaService.addSala(salaDodavanjeDTO);

        return new ResponseEntity<String>("Uspesno dodavanje sale", HttpStatus.OK);
    }

    @PostMapping(value = "/pretraga")
    public ResponseEntity<SalaPretragaDTO> saleSearch(@RequestBody SalaPretragaDTO salaPretragaDTO){
        return new ResponseEntity<>(SalaService.salaSearch(salaPretragaDTO), HttpStatus.OK);
    }
}
