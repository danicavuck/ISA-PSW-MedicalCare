package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.Administrator.AdminKlinickogCentra;
import com.groupfour.MedicalCare.Model.Administrator.AdminKlinike;
import com.groupfour.MedicalCare.Model.DTO.LoginDTO;
import com.groupfour.MedicalCare.Model.DTO.PacijentDTO;
import com.groupfour.MedicalCare.Model.DTO.UserRole;
import com.groupfour.MedicalCare.Model.HibernateUtil;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Model.Osoblje.MedicinskaSestra;
import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;
import com.groupfour.MedicalCare.Repository.*;
import com.groupfour.MedicalCare.Utill.PasswordCheck;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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

    public static ResponseEntity<String> loginPacijent(@RequestBody LoginDTO loginDTO){
        // Pretraga svih entiteta
        Pacijent pacijent = pacijentRepository.findUserByEmail(loginDTO.getEmail());
        AdminKlinickogCentra adminKlinickogCentra = adminKCRepository.findAdminKlinickogCentraByEmail(loginDTO.getEmail());
        AdminKlinike adminKlinike = adminKlinikeRepository.findAdminKlinikeByEmail(loginDTO.getEmail());
        Lekar lekar = lekarRepository.findLekarByEmail(loginDTO.getEmail());
        MedicinskaSestra medicinskaSestra = medicinskaSestraRepository.findMedicinskaSestraByEmail(loginDTO.getEmail());


        if(pacijent != null && PasswordCheck.verifyHash(loginDTO.getLozinka(), pacijent.getLozinka())){
            return new ResponseEntity<>("pacijent", HttpStatus.OK);
        }

        else if(adminKlinickogCentra != null && PasswordCheck.verifyHash(loginDTO.getLozinka(), adminKlinickogCentra.getLozinka())){
            return new ResponseEntity<>("admin_kc", HttpStatus.OK);
        }

        else if(adminKlinike != null && PasswordCheck.verifyHash(loginDTO.getLozinka(), adminKlinike.getLozinka())){
            return new ResponseEntity<>("admin_klinike", HttpStatus.OK);
        }

        else if(lekar != null && PasswordCheck.verifyHash(loginDTO.getLozinka(), lekar.getLozinka())){
            return new ResponseEntity<>("lekar", HttpStatus.OK);
        }

        else if(medicinskaSestra != null && PasswordCheck.verifyHash(loginDTO.getLozinka(), medicinskaSestra.getLozinka())){
            return new ResponseEntity<>("med_sestra", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Incorrect credentials", HttpStatus.FORBIDDEN);
        }

    }

}
