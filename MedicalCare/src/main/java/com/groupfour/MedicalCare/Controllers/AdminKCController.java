package com.groupfour.MedicalCare.Controllers;

import com.groupfour.MedicalCare.Model.Administrator.AdminKlinickogCentra;
import com.groupfour.MedicalCare.Model.Zahtevi.RegistracijaPacijenta;
import com.groupfour.MedicalCare.Repository.AdminKCRepository;
import com.groupfour.MedicalCare.Service.AdminKCService;
import com.groupfour.MedicalCare.Service.RegistracijaPacijentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    public AdminKCController(AdminKCService adminKCService){
        this.adminKCService = adminKCService;
    }



    @RequestMapping(value = "/zahtevi", method = RequestMethod.GET)
    public ResponseEntity<?> getZahteviZaRegistraciju(){
        List<RegistracijaPacijenta> temp = registracijaPacijentaService.getAllActive();
        System.out.println("uslo");
        for(int i = 0 ; i < temp.size() ; i++){

            System.out.println(temp.get(i).getIme());

        }

        return new ResponseEntity<>(registracijaPacijentaService.getAllActive(), HttpStatus.OK);
    }

}
