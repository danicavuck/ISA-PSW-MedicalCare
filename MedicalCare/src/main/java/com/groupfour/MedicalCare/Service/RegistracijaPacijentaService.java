package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.Administrator.AdminKlinickogCentra;
import com.groupfour.MedicalCare.Model.Zahtevi.RegistracijaPacijenta;
import com.groupfour.MedicalCare.Repository.AdminKCRepository;
import com.groupfour.MedicalCare.Repository.RegistracijaPacijentaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class RegistracijaPacijentaService {

    private AdminKCRepository adminKCRepository;
    private RegistracijaPacijentaRepository registracijaPacijentaRepository;
    private static Logger logger = LoggerFactory.getLogger(AdminKlinickogCentra.class);

    @Autowired
    public RegistracijaPacijentaService(RegistracijaPacijentaRepository registracijaPacijentaRepository,AdminKCRepository adminkcRepo) {
        this.registracijaPacijentaRepository = registracijaPacijentaRepository;
        this.adminKCRepository = adminkcRepo;
    }

    public List<RegistracijaPacijenta> getAllActive(HttpSession session) {
        AdminKlinickogCentra adminKlinickogCentra = adminKCRepository.findAdminKlinickogCentraById((int)session.getAttribute("id"));

        if(adminKlinickogCentra == null){
            logger.error("Nje pronadjen admin klinickog entra");
            return  null;
        }

        List<RegistracijaPacijenta> temp = new ArrayList<>();
        List<RegistracijaPacijenta> all = registracijaPacijentaRepository.findAll();

        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).isAktivan()) {
                temp.add(all.get(i));
            }
        }

        return temp;

    }

}
