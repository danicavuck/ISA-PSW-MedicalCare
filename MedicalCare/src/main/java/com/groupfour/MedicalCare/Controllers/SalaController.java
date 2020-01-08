package com.groupfour.MedicalCare.Controllers;

import com.groupfour.MedicalCare.Model.DTO.SalaDodavanjeDTO;
import com.groupfour.MedicalCare.Model.DTO.SalaPretragaDTO;
import com.groupfour.MedicalCare.Service.SalaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
@CrossOrigin
@RequestMapping("/sale")
public class SalaController {

    public SalaController() {
    }

    @GetMapping
    public ResponseEntity<ArrayList<SalaPretragaDTO>> vratiSaleZaSvakuKliniku(HttpSession session) {
//        if(!session.getAttribute("role").equals("admin_klinike"))
//            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        ArrayList<SalaPretragaDTO> sale = SalaService.getSale(0);
        return new ResponseEntity<>(sale, HttpStatus.OK);
    }

    @GetMapping(value = "/{klinikaId}")
    public ResponseEntity<ArrayList<SalaPretragaDTO>> vratiSaleZaOdredjenuKliniku(@PathVariable(value = "klinikaId") Integer klinikaId, HttpSession session) {
        System.out.println("Session id: " + session);
        System.out.println("ATRIBUTI SESIJE: ");
        System.out.println(session.getAttributeNames());
//        if(session.getAttribute("role") == null || !session.getAttribute("role").equals("admin_klinike"))
////            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        ArrayList<SalaPretragaDTO> sale = SalaService.getSale(klinikaId);
        return new ResponseEntity<>(sale, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteSala(@RequestBody SalaPretragaDTO salaPretragaDTO) {
        SalaService.deleteSala(salaPretragaDTO);

        return new ResponseEntity<String>("Uspesno izvrseno brisanje", HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<?> addSala(@RequestBody SalaDodavanjeDTO salaDodavanjeDTO) {
        SalaService.addSala(salaDodavanjeDTO);

        return new ResponseEntity<String>("Uspesno dodavanje sale", HttpStatus.OK);
    }

    @PostMapping(value = "/pretraga")
    public ResponseEntity<?> saleSearch(@RequestBody SalaPretragaDTO salaPretragaDTO) {
        return SalaService.pretraziSaluPoBrojuSale(salaPretragaDTO);
    }
}
