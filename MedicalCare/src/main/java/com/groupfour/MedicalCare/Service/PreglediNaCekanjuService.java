package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.DTO.PregledDTO;
import com.groupfour.MedicalCare.Model.Pregled.PreglediNaCekanju;
import com.groupfour.MedicalCare.Repository.PreglediNaCekanjuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Service
public class PreglediNaCekanjuService {
    private static PreglediNaCekanjuRepository preglediNaCekanjuRepository;

    @Autowired
    PreglediNaCekanjuService(PreglediNaCekanjuRepository pregledi){
        preglediNaCekanjuRepository = pregledi;
    }

    public static ResponseEntity<?> sviPreglediNaCekanjuZaKliniku(Integer klinikaId) {
        ArrayList<PreglediNaCekanju> preglediNaCekanju =
                preglediNaCekanjuRepository.getPreglediNaCekanjuByKlinikaId(klinikaId);
        if(preglediNaCekanju != null){
            ArrayList<PregledDTO> preglediDTO = new ArrayList<>();
            for(PreglediNaCekanju pregled : preglediNaCekanju){
                preglediDTO.add(mapiraniPregled(pregled));
            }

            return new ResponseEntity<>(preglediDTO, HttpStatus.OK);
            //return new ResponseEntity<>(preglediNaCekanju, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public static PregledDTO mapiraniPregled(PreglediNaCekanju preglediNaCekanju){
        LocalDateTime pocetak = preglediNaCekanju.getTerminPregleda();
        LocalDateTime kraj = pocetak.plusMinutes(preglediNaCekanju.getTrajanjePregleda());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
        String pocetakTermina = formatter.format(pocetak);
        String krajTermina = formatter.format(kraj);

        return PregledDTO.builder().id(preglediNaCekanju.getId()).pocetakTermina(pocetakTermina).krajTermina(krajTermina).tipPregleda(preglediNaCekanju.getTipPregleda().getTipPregleda()).build();
    }
}
