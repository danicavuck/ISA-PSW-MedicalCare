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

     @Autowired
    public LekarService(LekarRepository lRepository){
        lekarRepository = lRepository;
    }

    public List<Lekar> getLekari(){return lekarRepository.findAll(); }




    public static ArrayList<LekarDTO> getLekareDTO(Integer klinikaId){
        ArrayList<Lekar> lekari = lekarRepository.findAll();
        ArrayList<LekarDTO> lekariDTO = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();

        if(klinikaId == 0){
            for(Lekar lekar : lekari) {
                lekariDTO.add(mapper.map(lekar, LekarDTO.class));
            }
            return lekariDTO;
        } else{
            for(Lekar lekar : lekari) {
                if(lekar.getKlinika().getId() == klinikaId) {
                    lekariDTO.add(mapper.map(lekar, LekarDTO.class));
                }
            }
            return lekariDTO;
        }

    }

}
