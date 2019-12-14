package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Repository.LekarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LekarService {
    @Autowired
    private LekarRepository lekarRepository;



    public List<Lekar> getLekari(){return lekarRepository.findAll(); }


}
