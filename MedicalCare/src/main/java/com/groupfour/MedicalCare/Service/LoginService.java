package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.Administrator.AdminKlinickogCentra;
import com.groupfour.MedicalCare.Model.Administrator.AdminKlinike;
import com.groupfour.MedicalCare.Model.DTO.LoginDTO;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Model.Osoblje.MedicinskaSestra;
import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;
import com.groupfour.MedicalCare.Repository.*;
import com.groupfour.MedicalCare.Utill.PasswordCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

@Service
public class LoginService {

    private static PacijentRepository pacijentRepository;
    private static AdminKCRepository adminKCRepository;
    private static AdminKlinikeRepository adminKlinikeRepository;
    private static LekarRepository lekarRepository;
    private static MedicinskaSestraRepository medicinskaSestraRepository;

    @Autowired
    public LoginService(PacijentRepository pRepository, AdminKCRepository aKCRepository, AdminKlinikeRepository aKlinike,
                        LekarRepository lRepo, MedicinskaSestraRepository mSestraRepository){
        pacijentRepository = pRepository;
        adminKCRepository = aKCRepository;
        adminKlinikeRepository = aKlinike;
        lekarRepository = lRepo;
        medicinskaSestraRepository = mSestraRepository;

    }

    public static ResponseEntity<String> loginPacijent(@RequestBody LoginDTO loginDTO, HttpServletRequest request){
        // Pretraga svih entiteta
        Pacijent pacijent = pacijentRepository.findUserByEmail(loginDTO.getEmail());
        AdminKlinickogCentra adminKlinickogCentra = adminKCRepository.findAdminKlinickogCentraByEmail(loginDTO.getEmail());
        AdminKlinike adminKlinike = adminKlinikeRepository.findAdminKlinikeByEmail(loginDTO.getEmail());
        Lekar lekar = lekarRepository.findLekarByEmail(loginDTO.getEmail());
        MedicinskaSestra medicinskaSestra = medicinskaSestraRepository.findMedicinskaSestraByEmail(loginDTO.getEmail());


        if(pacijent != null && PasswordCheck.verifyHash(loginDTO.getLozinka(), pacijent.getLozinka())){
            request.getSession().setAttribute("role", "pacijent");
            return new ResponseEntity<>("pacijent", HttpStatus.OK);
        }

        else if(adminKlinickogCentra != null && PasswordCheck.verifyHash(loginDTO.getLozinka(), adminKlinickogCentra.getLozinka())){
            request.getSession().setAttribute("role", "admin_kc");
            return new ResponseEntity<>("admin_kc", HttpStatus.OK);
        }

        else if(adminKlinike != null && PasswordCheck.verifyHash(loginDTO.getLozinka(), adminKlinike.getLozinka())){
            request.getSession().setAttribute("role", "admin_klinike");
            return new ResponseEntity<>("admin_klinike", HttpStatus.OK);
        }

        else if(lekar != null && PasswordCheck.verifyHash(loginDTO.getLozinka(), lekar.getLozinka())){
            request.getSession().setAttribute("role", "lekar");
            return new ResponseEntity<>("lekar", HttpStatus.OK);
        }

        else if(medicinskaSestra != null && PasswordCheck.verifyHash(loginDTO.getLozinka(), medicinskaSestra.getLozinka())){
            request.getSession().setAttribute("role", "med_sestra");
            return new ResponseEntity<>("med_sestra", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Incorrect credentials", HttpStatus.FORBIDDEN);
        }

    }

    public static ResponseEntity<String> logoutPacijent(){
        return new ResponseEntity<>("Successfull logout", HttpStatus.OK);
    }

}
