package com.groupfour.MedicalCare.Controllers;

import com.groupfour.MedicalCare.Model.DTO.KlinikaDTO;
import com.groupfour.MedicalCare.Model.Klinika.Klinika;
import com.groupfour.MedicalCare.Service.KlinikaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/adminklinike")
public class AdminKlinikeController {

    @GetMapping
    public ResponseEntity<String> adminKlinikeHomepage(HttpServletRequest request){
//        if(request.getSession().getAttribute("role") == null || !request.getSession().getAttribute("role").equals("admin_klinike")){
//            return new ResponseEntity<>("Unauthorized request", HttpStatus.FORBIDDEN);
//
//        }
        return new ResponseEntity<>("OK Admine", HttpStatus.OK);
    }

    @GetMapping("/klinike")
    public ResponseEntity<List<KlinikaDTO>> getKlinike(){
        ArrayList<KlinikaDTO> klinike = KlinikaService.getKlinike();

        return new ResponseEntity<>(klinike, HttpStatus.OK);
    }
}
