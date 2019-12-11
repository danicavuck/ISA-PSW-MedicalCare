package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.Administrator.AdminKlinickogCentra;
import com.groupfour.MedicalCare.Repository.AdminKCRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminKCService {

    private static AdminKCRepository adminKCRepository;

    public AdminKCService(AdminKCRepository adminKCRepository) {
        this.adminKCRepository = adminKCRepository;
    }

    public AdminKlinickogCentra getByEmail(String email, String lozinka){
        AdminKlinickogCentra adminKC = adminKCRepository.findAdminKlinickogCentraByEmail(email);

        if(adminKC.getLozinka().equals(lozinka)){
            return adminKC;
        }
        return null;
    }

    public AdminKlinickogCentra promenaLozinke(AdminKlinickogCentra adminKlinickogCentra) {
        adminKlinickogCentra.setPrviPutLogovan(false);
        return adminKCRepository.save(adminKlinickogCentra);
    }

}
