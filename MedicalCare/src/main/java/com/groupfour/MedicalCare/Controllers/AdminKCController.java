package com.groupfour.MedicalCare.Controllers;

import com.groupfour.MedicalCare.Model.DTO.KlinikaDTO;
import com.groupfour.MedicalCare.Model.Zahtevi.RegistracijaPacijenta;
import com.groupfour.MedicalCare.Service.AdminKCService;
import com.groupfour.MedicalCare.Service.RegistracijaPacijentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/adminkc")
public class AdminKCController {

    @Autowired
    private AdminKCService adminKCService;
    @Autowired
    private RegistracijaPacijentaService registracijaPacijentaService;


    @Autowired
    public AdminKCController(AdminKCService adminKCService) {
        this.adminKCService = adminKCService;
    }


    @RequestMapping(value = "/zahtevi", method = RequestMethod.GET)
    public ResponseEntity<?> getZahteviZaRegistraciju() {
        List<RegistracijaPacijenta> temp = registracijaPacijentaService.getAllActive();

        return new ResponseEntity<>(registracijaPacijentaService.getAllActive(), HttpStatus.OK);
    }


    @RequestMapping(value = "/prihvatiZahtev", method = RequestMethod.PUT)
    public ResponseEntity<?> prihvatiZahtev(@RequestBody Integer id) {
        adminKCService.prihvatiZahtev(id);
        System.out.println("prihvacen");
        return new ResponseEntity<String>("Zahtev je prihvacen!", HttpStatus.OK);

    }

    @RequestMapping(value = "/odbijZahtev", method = RequestMethod.PUT)
    public ResponseEntity<?> odbijZahtev(@RequestBody Integer id) {
        System.out.println("odbijen");
        adminKCService.odbijZahtev(id);
        return new ResponseEntity<String>("Zahtev je odbijen!", HttpStatus.OK);

    }

    @PostMapping("/dodajKliniku")
    public ResponseEntity<String> register(@RequestBody KlinikaDTO klinikaDTO) {
        return adminKCService.dodajKliniku(klinikaDTO);
    }


}
