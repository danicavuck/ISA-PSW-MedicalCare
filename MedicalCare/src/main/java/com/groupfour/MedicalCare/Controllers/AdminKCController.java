package com.groupfour.MedicalCare.Controllers;

import com.groupfour.MedicalCare.Model.DTO.*;
import com.groupfour.MedicalCare.Model.Zahtevi.RegistracijaPacijenta;
import com.groupfour.MedicalCare.Service.AdminKCService;
import com.groupfour.MedicalCare.Service.RegistracijaPacijentaService;
import com.groupfour.MedicalCare.Utill.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@CrossOrigin(allowCredentials = "true")
@RequestMapping("/adminkc")
public class AdminKCController {

    private Authorization authorization;
    private String[] role = {"adminkc"};


    private AdminKCService adminKCService;

    private RegistracijaPacijentaService registracijaPacijentaService;


    @Autowired
    public AdminKCController(AdminKCService adminKCService , RegistracijaPacijentaService rpRepo , Authorization auto) {
        this.registracijaPacijentaService = rpRepo;
        this.authorization = auto;
        this.adminKCService = adminKCService;
    }


    @RequestMapping(value = "/zahtevi", method = RequestMethod.GET)
    public ResponseEntity<?> getZahteviZaRegistraciju(HttpSession session) {
        System.out.println(session.getAttribute("role"));
        System.out.println(session.getAttribute("id"));
          if(authorization.hasPermisson(session,role)) {
            return new ResponseEntity<>(registracijaPacijentaService.getAllActive(session), HttpStatus.OK);
        }

       return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/dijagnoze", method = RequestMethod.GET)
    public ResponseEntity<?> getDijagnoze(HttpSession session) {
        System.out.println(session.getAttribute("role"));
        System.out.println(session.getAttribute("id"));
        if(authorization.hasPermisson(session,new String[] {"lekar","adminkc"})) {
        return new ResponseEntity<>(adminKCService.getDijagnoze(session), HttpStatus.OK);
         }

        return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/lekovi", method = RequestMethod.GET)
    public ResponseEntity<?> getLekovi(HttpSession session) {
        System.out.println(session.getAttribute("role"));
        System.out.println(session.getAttribute("id"));
        if(authorization.hasPermisson(session,new String[] {"lekar","adminkc"})) {
            return new ResponseEntity<>(adminKCService.getLekovi(session), HttpStatus.OK);
        }

        return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
    }


    @RequestMapping(value = "/prihvatiZahtev", method = RequestMethod.PUT)
    public ResponseEntity<?> prihvatiZahtev(@RequestBody RegistracijaPacijentaDTO registracijaPacijentaDTO, HttpSession session) {
        if (authorization.hasPermisson(session, role)) {
            adminKCService.prihvatiZahtev(registracijaPacijentaDTO,session);
           return new ResponseEntity<String>("Zahtev je prihvacen!", HttpStatus.OK);
        }

       return new ResponseEntity<String>("Zahtev nije prihvacen!", HttpStatus.UNAUTHORIZED);

    }

    @RequestMapping(value = "/odbijZahtev", method = RequestMethod.PUT)
    public ResponseEntity<?> odbijZahtev(@RequestBody RegistracijaPacijentaDTO registracijaPacijentaDTO, HttpSession session) {
        if(authorization.hasPermisson(session,role)) {
            adminKCService.odbijZahtev(registracijaPacijentaDTO,session);
            return new ResponseEntity<String>("Zahtev je odbijen!", HttpStatus.OK);
        }

        return new ResponseEntity<String>("Zahtev nije odbijen!",HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/dodajKliniku", method = RequestMethod.POST)
    public ResponseEntity<?> dodajKliniku(@RequestBody KlinikaBazicnoDTO klinikaBazicnoDTO, HttpSession session) {
        if(authorization.hasPermisson(session,role)) {
            return adminKCService.dodajKliniku(klinikaBazicnoDTO,session);
        }
        return new ResponseEntity<>("Dodavanje nije dozvoljeno!",HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/dodajAdminaKlinike", method = RequestMethod.POST)
    public ResponseEntity<?> dodajAdminaKlinike(@RequestBody AdminKlinikeBazicnoDTO klinikaBazicnoDTO, HttpSession session) {
        if(authorization.hasPermisson(session,role)) {
            return adminKCService.dodajAdminaKlinike(klinikaBazicnoDTO,session);
        }
        return new ResponseEntity<>("Dodavanje nije dozvoljeno!",HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/dodajDijagnozu")
    public ResponseEntity<?> dodajDijagnozu(@RequestBody DijagnozaDTO dijagnozaDTO, HttpSession session) {
        if(authorization.hasPermisson(session,role)) {
            return adminKCService.dodajDijagnozu(dijagnozaDTO,session);
        }
        return new ResponseEntity<>("Dodavanje nije dozvoljeno!",HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/dodajLek")
    public ResponseEntity<?> dodajLek(@RequestBody LekDTO lekDTO, HttpSession session) {
        if(authorization.hasPermisson(session,role)) {
            return adminKCService.dodajLek(lekDTO,session);
        }
        return new ResponseEntity<>("Dodavanje nije dozvoljeno!",HttpStatus.UNAUTHORIZED);
    }

}
