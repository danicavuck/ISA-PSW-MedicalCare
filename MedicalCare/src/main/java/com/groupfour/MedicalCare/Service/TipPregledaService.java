package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.DTO.TipPregledaDTO;
import com.groupfour.MedicalCare.Model.Pregled.TipPregleda;
import com.groupfour.MedicalCare.Repository.TipPregledaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TipPregledaService {
    private static TipPregledaRepository tipPregledaRepository;

    @Autowired
    public TipPregledaService(TipPregledaRepository tRepo){
        tipPregledaRepository = tRepo;
    }

    public static ArrayList<TipPregledaDTO> getTipPregleda(){
        ArrayList<TipPregleda> tipPregleda = tipPregledaRepository.findAll();
        ArrayList<TipPregledaDTO> tipPregledaDTO = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();

        for(TipPregleda tp : tipPregleda){
            if(tp.isAktivan()){
                tipPregledaDTO.add(modelMapper.map(tp, TipPregledaDTO.class));
            }
        }
        return tipPregledaDTO;
    }

    public static void addTipPregleda(TipPregledaDTO tipPregledaDTO){
        TipPregleda tipPregleda =
                TipPregleda.builder().tipPregleda(tipPregledaDTO.getTipPregleda()).aktivan(true).predefinisan(true).build();
        System.out.println("Dodavanje tipa pregleda: " + tipPregleda.getTipPregleda());
        tipPregledaRepository.save(tipPregleda);
    }
}
