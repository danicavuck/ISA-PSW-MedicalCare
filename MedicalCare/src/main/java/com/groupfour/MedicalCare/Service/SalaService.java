package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.Klinika.Sala;
import com.groupfour.MedicalCare.Repository.SalaRepository;
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

    public static ArrayList<Sala> getSale(){
       return salaRepository.findAll();
    }
}
