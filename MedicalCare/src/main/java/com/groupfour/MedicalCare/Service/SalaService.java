package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.DTO.SalaPretragaDTO;
import com.groupfour.MedicalCare.Model.Klinika.Sala;
import com.groupfour.MedicalCare.Repository.SalaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SalaService {
    private static SalaRepository salaRepository;

    @Autowired
    public SalaService(SalaRepository sRepository){
        salaRepository = sRepository;
    }

    public static ArrayList<SalaPretragaDTO> getSale(){
        ArrayList<Sala> sale = salaRepository.findAll();
        ArrayList<SalaPretragaDTO> saleDTO = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();

        for(Sala s : sale){
            saleDTO.add(mapper.map(s, SalaPretragaDTO.class));
        }

        return saleDTO;
    }
}
