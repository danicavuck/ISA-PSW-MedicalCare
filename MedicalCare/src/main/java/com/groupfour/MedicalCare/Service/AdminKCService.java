package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.Administrator.AdminKlinickogCentra;
import com.groupfour.MedicalCare.Model.Zahtevi.RegistracijaPacijenta;
import com.groupfour.MedicalCare.Repository.AdminKCRepository;
import com.groupfour.MedicalCare.Repository.RegistracijaPacijentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OptimisticLockException;

@Service
public class AdminKCService {
    @Autowired
    private static AdminKCRepository adminKCRepository;
    @Autowired
    private RegistracijaPacijentaRepository registracijaPacijentaRepository;

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

    @Transactional(readOnly = false)
    public void prihvatiZahtev(Integer id) {

      try {
          RegistracijaPacijenta registracijaPacijenta = registracijaPacijentaRepository.getOne(id);
          registracijaPacijenta.setOdobren(true);
          registracijaPacijenta.setAktivan(false);
          System.out.println("u servisu");
          registracijaPacijentaRepository.save(registracijaPacijenta);
      }catch(OptimisticLockException e){
          System.out.println("exception");
      }
    }

    @Transactional(readOnly = false)
    public void odbijZahtev(Integer id) {
          try{
                RegistracijaPacijenta registracijaPacijenta = registracijaPacijentaRepository.getOne(id);
            registracijaPacijenta.setOdobren(false);
            registracijaPacijenta.setAktivan(false);
            registracijaPacijentaRepository.save(registracijaPacijenta);
        } catch (OptimisticLockException e) {
              System.out.println("exception");
            // osvesi sajt
        }

    }


}
