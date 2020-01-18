package com.groupfour.MedicalCare.Service;


import com.groupfour.MedicalCare.Model.Administrator.AdminKlinike;
import com.groupfour.MedicalCare.Model.DTO.KlinikaDTO;
import com.groupfour.MedicalCare.Model.DTO.LekarDTO;
import com.groupfour.MedicalCare.Model.Klinika.Klinika;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Repository.AdminKlinikeRepository;
import com.groupfour.MedicalCare.Repository.KlinikaRepository;
import com.groupfour.MedicalCare.Repository.LekarRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class KlinikaService {
    private static KlinikaRepository klinikaRepository;
    private static LekarRepository lekarRepository;
    private static AdminKlinikeRepository adminKlinikeRepository;
    private static Logger logger = LoggerFactory.getLogger(KlinikaService.class);

    @Autowired
    public KlinikaService(KlinikaRepository kRepository, LekarRepository lRepo,
                          AdminKlinikeRepository aklinikeRepository) {
        klinikaRepository = kRepository;
        lekarRepository = lRepo;
        adminKlinikeRepository = aklinikeRepository;
    }


    public static ArrayList<KlinikaDTO> getKlinike(HttpSession session) {
        AdminKlinike adminKlinike = adminKlinikeRepository.findAdminKlinikeById((int) session.getAttribute("id"));
        ArrayList<KlinikaDTO> klinikeDTO = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
        if(adminKlinike == null)
        {
            logger.error("Nije pronadjen admin klinike");
            return null;
        }

        klinikeDTO.add(mapper.map(adminKlinike.getKlinika(), KlinikaDTO.class));
        return klinikeDTO;
    }

    public static boolean updateKlinika(KlinikaDTO klinikaDTO) {
        Klinika klinika = klinikaRepository.findById(klinikaDTO.getId());
        if (klinika == null) {
            return false;
        }

        klinika.setNaziv(klinikaDTO.getNaziv());
        klinika.setAdresa(klinikaDTO.getAdresa());
        klinika.setOpis(klinikaDTO.getOpis());

        klinikaRepository.save(klinika);
        return true;
    }

    public static List<LekarDTO> getLekari() {
        List<Lekar> lekari = lekarRepository.findAll();
        List<LekarDTO> temp = new ArrayList<LekarDTO>();
        ModelMapper mapper = new ModelMapper();
        for (Lekar l : lekari) {
            temp.add(mapiranjeLekara(l));
        }
        return temp;
    }

    public static LekarDTO mapiranjeLekara(Lekar lekar) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(lekar, LekarDTO.class);
    }

}
