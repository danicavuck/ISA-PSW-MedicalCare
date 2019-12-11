package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.Klinika.Klinika;
import com.groupfour.MedicalCare.Repository.KlinikaRepository;
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

    public static ArrayList<Klinika> getKlinike(){
        return klinikaRepository.findAll();
    }
}
