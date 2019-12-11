package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.Zahtevi.RegistracijaPacijenta;
import com.groupfour.MedicalCare.Repository.RegistracijaPacijentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegistracijaPacijentaService {

    @Autowired
    private RegistracijaPacijentaRepository registracijaPacijentaRepository;

    public RegistracijaPacijentaService(RegistracijaPacijentaRepository registracijaPacijentaRepository) {
        this.registracijaPacijentaRepository = registracijaPacijentaRepository;
    }

    public List<RegistracijaPacijenta> getAllActive(){
        List<RegistracijaPacijenta> temp = new ArrayList<>();
        List<RegistracijaPacijenta> all = registracijaPacijentaRepository.findAll();
        System.out.println(all.size());
        for(int i = 0 ; i < all.size() ; i++){
            if(all.get(i).isAktivan()){
                System.out.println(all.get(i).getIme());
                temp.add(all.get(i));
            }
        }

        return  temp;

    }

}
