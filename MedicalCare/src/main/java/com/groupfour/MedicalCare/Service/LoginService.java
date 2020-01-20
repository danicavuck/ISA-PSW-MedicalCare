package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.Administrator.AdminKlinickogCentra;
import com.groupfour.MedicalCare.Model.Administrator.AdminKlinike;
import com.groupfour.MedicalCare.Model.DTO.LoginDTO;
import com.groupfour.MedicalCare.Model.DTO.UserRole;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Model.Osoblje.MedicinskaSestra;
import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;
import com.groupfour.MedicalCare.Repository.*;
import com.groupfour.MedicalCare.Utill.PasswordCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;

@Service
public class LoginService {

    private static PacijentRepository pacijentRepository;
    private static AdminKCRepository adminKCRepository;
    private static AdminKlinikeRepository adminKlinikeRepository;
    private static LekarRepository lekarRepository;
    private static MedicinskaSestraRepository medicinskaSestraRepository;
    private static Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    public LoginService(PacijentRepository pRepository, AdminKCRepository aKCRepository, AdminKlinikeRepository aKlinike,
                        LekarRepository lRepo, MedicinskaSestraRepository mSestraRepository) {
        pacijentRepository = pRepository;
        adminKCRepository = aKCRepository;
        adminKlinikeRepository = aKlinike;
        lekarRepository = lRepo;
        medicinskaSestraRepository = mSestraRepository;

    }

    public static ResponseEntity<UserRole> loginPacijent(@RequestBody LoginDTO loginDTO, HttpSession session) {
        Pacijent pacijent = pacijentRepository.findUserByEmail(loginDTO.getEmail());
        AdminKlinickogCentra adminKlinickogCentra = null;
        AdminKlinike adminKlinike = null;
        Lekar lekar = null;
        MedicinskaSestra medicinskaSestra = null;
        if(pacijent == null) {
            adminKlinickogCentra = adminKCRepository.findAdminKlinickogCentraByEmail(loginDTO.getEmail());
        }
        if(adminKlinickogCentra == null && pacijent == null){
            adminKlinike = adminKlinikeRepository.findAdminKlinikeByEmail(loginDTO.getEmail());
        }
        if(adminKlinike == null && adminKlinickogCentra == null && pacijent == null) {
            lekar = lekarRepository.findLekarByEmail(loginDTO.getEmail());
        }
        if(lekar == null && adminKlinike == null && adminKlinickogCentra == null && pacijent == null) {
            medicinskaSestra =  medicinskaSestraRepository.findMedicinskaSestraByEmail(loginDTO.getEmail());
        }

            logger.info("Finding user by username & password: ");
            logger.info(loginDTO.getEmail() +loginDTO.getLozinka());
        if (pacijent != null && PasswordCheck.verifyHash(loginDTO.getLozinka(), pacijent.getLozinka())) {
            session.setAttribute("id", pacijent.getId());
            session.setAttribute("role", "pacijent");
            UserRole userRole = UserRole.builder().user_email(pacijent.getEmail()).role("pacijent").build();
            return new ResponseEntity<>(userRole, HttpStatus.OK);
        } else if (adminKlinickogCentra != null && PasswordCheck.verifyHash(loginDTO.getLozinka(), adminKlinickogCentra.getLozinka())) {
            session.setAttribute("id", adminKlinickogCentra.getId());
            session.setAttribute("role", "adminkc");
            UserRole userRole = UserRole.builder().user_email(adminKlinickogCentra.getEmail()).role("admin_kc").build();
            return ResponseEntity.ok().body(userRole);
        } else if (adminKlinike != null /*&& PasswordCheck.verifyHash(loginDTO.getLozinka(),
                adminKlinike.getLozinka())*/) {
            session.setAttribute("id", adminKlinike.getId());
            session.setAttribute("role", "adminklinike");
            logger.info("Startovana sesija role:" + session.getAttribute("role") + " id: " + session.getAttribute("id"));
            UserRole userRole = UserRole.builder().user_email(adminKlinike.getEmail()).role("admin_klinike").build();
            return new ResponseEntity<>(userRole, HttpStatus.OK);
        } else if (lekar != null && PasswordCheck.verifyHash(loginDTO.getLozinka(), lekar.getLozinka())) {
            session.setAttribute("id", lekar.getId());
            session.setAttribute("role", "lekar");
            UserRole userRole = UserRole.builder().user_email(lekar.getEmail()).role("lekar").build();
            return new ResponseEntity<>(userRole, HttpStatus.OK);
        } else if (medicinskaSestra != null && PasswordCheck.verifyHash(loginDTO.getLozinka(), medicinskaSestra.getLozinka())) {
            session.setAttribute("id", medicinskaSestra.getId());
            session.setAttribute("role", "med_sestra");
            UserRole userRole = UserRole.builder().user_email(medicinskaSestra.getEmail()).role("med_sestra").build();
            return new ResponseEntity<>(userRole, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

    }

    public static ResponseEntity<?> logoutUser(HttpSession session) {
        try {
            logger.info("Trying to invalidate session");
            session.invalidate();
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An Error occured while invalidating session");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
