package com.groupfour.MedicalCare.Service;


import com.groupfour.MedicalCare.Model.DTO.KlinikaDTO;
import com.groupfour.MedicalCare.Model.DTO.LekarDTO;
import com.groupfour.MedicalCare.Model.Klinika.Klinika;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Repository.KlinikaRepository;
import com.groupfour.MedicalCare.Repository.LekarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KlinikaService {
    @Autowired
    private static KlinikaRepository klinikaRepository;

    @Autowired
    private static LekarRepository lekarRepository;

    @Autowired
    public KlinikaService(KlinikaRepository kRepository, LekarRepository lRepo) {
        klinikaRepository = kRepository;
        lekarRepository = lRepo;
    }


    public static ArrayList<KlinikaDTO> getKlinike() {
        ArrayList<Klinika> klinike = klinikaRepository.findAll();
        ArrayList<KlinikaDTO> klinikeDTO = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();

        for (Klinika k : klinike) {
            // Samo jednu kliniku vraca
            if (k.getId() == 5)
                klinikeDTO.add(mapper.map(k, KlinikaDTO.class));
        }

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
