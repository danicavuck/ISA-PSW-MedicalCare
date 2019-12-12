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
    private static LekarRepository lekarRepository;

    @Autowired
    public LekarService(LekarRepository lRepository){
        lekarRepository = lRepository;
    }

    public static ArrayList<LekarDTO> getLekareDTO(){
        ArrayList<Lekar> lekari = lekarRepository.findAll();
        ArrayList<LekarDTO> lekariDTO = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();

        for(Lekar lekar : lekari) {
            lekariDTO.add(mapper.map(lekar, LekarDTO.class));
        }

        return lekariDTO;
    }
}
