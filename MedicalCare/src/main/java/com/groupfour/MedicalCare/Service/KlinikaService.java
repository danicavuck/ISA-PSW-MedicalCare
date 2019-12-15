package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.Administrator.AdminKlinike;
import com.groupfour.MedicalCare.Model.DTO.KlinikaDTO;
import com.groupfour.MedicalCare.Model.DTO.LekarDTO;
import com.groupfour.MedicalCare.Model.HibernateUtil;
import com.groupfour.MedicalCare.Model.Klinika.Klinika;
import com.groupfour.MedicalCare.Model.Klinika.Sala;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Model.Osoblje.MedicinskaSestra;
import com.groupfour.MedicalCare.Repository.*;
import org.hibernate.Session;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KlinikaService {
    @Autowired
    private static KlinikaRepository klinikaRepository;
    @Autowired
    private LekarRepository lekarRepository;
    @Autowired
    private MedicinskaSestraRepository medicinskaSestraRepository;
    @Autowired
    private AdminKlinikeRepository adminKlinikeRepository;
    @Autowired
    private SalaRepository salaRepository;


    @Autowired
    public KlinikaService(KlinikaRepository kRepository){
        klinikaRepository = kRepository;
    }


    public List<Klinika> getKlinike(){
        return klinikaRepository.findAll();
    }

    public List<LekarDTO> getLekari() {
        List<Lekar> lekari = lekarRepository.findAll();
        List<LekarDTO> temp = new ArrayList<LekarDTO>();
        ModelMapper mapper = new ModelMapper();
        for (Lekar l : lekari) {
            temp.add(mapper.map(l, LekarDTO.class));

        }
        return temp;
    }
    public List<MedicinskaSestra> getMedicinskeSestre(){ return medicinskaSestraRepository.findAll();}

    public List<AdminKlinike> getAdminiKlinike(){ return adminKlinikeRepository.findAll();}

    public List<Sala> getSale(){return salaRepository.findAll();}


}
