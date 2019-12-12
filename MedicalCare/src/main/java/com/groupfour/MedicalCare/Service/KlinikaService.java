package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.DTO.KlinikaDTO;
import com.groupfour.MedicalCare.Model.Klinika.Klinika;
import com.groupfour.MedicalCare.Repository.KlinikaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class KlinikaService {
    private static KlinikaRepository klinikaRepository;

    @Autowired
    public KlinikaService(KlinikaRepository kRepository){
        klinikaRepository = kRepository;
    }

    public static ArrayList<KlinikaDTO> getKlinike(){
        ArrayList<Klinika> klinike = klinikaRepository.findAll();
        ArrayList<KlinikaDTO> klinikeDTO = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();

        for(Klinika k : klinike){
            klinikeDTO.add(mapper.map(k, KlinikaDTO.class));
        }

        return klinikeDTO;
    }
}
