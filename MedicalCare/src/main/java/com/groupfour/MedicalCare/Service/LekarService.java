package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.DTO.DodavanjeLekaraDTO;
import com.groupfour.MedicalCare.Model.DTO.LekarDTO;
import com.groupfour.MedicalCare.Model.Klinika.Klinika;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Repository.KlinikaRepository;
import com.groupfour.MedicalCare.Repository.LekarRepository;
import com.groupfour.MedicalCare.Utill.EmailUniqueness;
import com.groupfour.MedicalCare.Utill.PasswordCheck;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LekarService {
    private static LekarRepository lekarRepository;
    private static KlinikaRepository klinikaRepository;

    @Autowired
    public LekarService(LekarRepository lRepository, KlinikaRepository kRepository) {
        lekarRepository = lRepository;
        klinikaRepository = kRepository;
    }

    public static ArrayList<LekarDTO> getLekareDTO(Integer klinikaId) {
        ArrayList<Lekar> lekari = lekarRepository.findAll();
        ArrayList<LekarDTO> lekariDTO = new ArrayList<>();

        for (Lekar lekar : lekari) {
            if (klinikaId == 0 || (lekar.getKlinika().getId() == klinikaId) && lekar.isAktivan()) {
                lekariDTO.add(mapirajLekara(lekar));
            }
        }
        return lekariDTO;
    }

    public static LekarDTO mapirajLekara(Lekar lekar) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(lekar, LekarDTO.class);
    }

    public static ResponseEntity<?> brisanjeLekara(LekarDTO lekarDTO){
        Lekar lekar = lekarRepository.findLekarById(lekarDTO.getId());
        if(lekar != null){
            lekar.setAktivan(false);
            lekarRepository.save(lekar);
            return new ResponseEntity<>("Uspesno izbrisan lekar", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("Neuspesno brisanje lekara", HttpStatus.BAD_REQUEST);
    }

    /*
    * Treba dodati i kliniku automatski, to treba raditi preko sesije, uzeti ID admina klinike, potom u kreiranju
    * objekta setovati id klinike za datog lekara
    *  */
    public static ResponseEntity<?> dodavanjeNovogLekara(DodavanjeLekaraDTO dodavanjeLekaraDTO){
        if(!EmailUniqueness.isEmailUniqe(dodavanjeLekaraDTO.getEmail())){
            return new ResponseEntity<>("Lekar sa datom mejl adresom vec postoji", HttpStatus.BAD_REQUEST);
        }
        Lekar lekar = konstrukcijaObjekta(dodavanjeLekaraDTO);
        lekarRepository.save(lekar);

        return new ResponseEntity<>("Uspesno dodavanje novog lekara.", HttpStatus.CREATED);
    }

    public static Lekar konstrukcijaObjekta(DodavanjeLekaraDTO dodavanjeLekaraDTO){
        Klinika klinika = klinikaRepository.findById(5);
        return Lekar.builder().ime(dodavanjeLekaraDTO.getIme()).prezime(dodavanjeLekaraDTO.getPrezime()).lozinka(PasswordCheck.hash(dodavanjeLekaraDTO.getLozinka())).email(dodavanjeLekaraDTO.getEmail()).aktivan(true).klinika(klinika).build();
    }

}
