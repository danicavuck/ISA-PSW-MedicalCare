package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.DTO.LekarDTO;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Repository.LekarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LekarService {
    @Autowired
    private static LekarRepository lekarRepository;

    @Autowired
    public LekarService(LekarRepository lRepository) {
        lekarRepository = lRepository;
    }

    public static ArrayList<LekarDTO> getLekareDTO(Integer klinikaId) {
        ArrayList<Lekar> lekari = lekarRepository.findAll();
        ArrayList<LekarDTO> lekariDTO = new ArrayList<>();

        for (Lekar lekar : lekari) {
            if (klinikaId == 0 || (lekar.getKlinika().getId() == klinikaId)) {
                lekariDTO.add(mapirajLekara(lekar));
            }
        }
        return lekariDTO;
    }

    public static LekarDTO mapirajLekara(Lekar lekar) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(lekar, LekarDTO.class);
    }

}
