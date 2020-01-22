package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.Administrator.AdminKlinike;
import com.groupfour.MedicalCare.Model.DTO.DodavanjeLekaraDTO;
import com.groupfour.MedicalCare.Model.DTO.LekarDTO;
import com.groupfour.MedicalCare.Model.Klinika.Klinika;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Repository.AdminKlinikeRepository;
import com.groupfour.MedicalCare.Repository.KlinikaRepository;
import com.groupfour.MedicalCare.Repository.LekarRepository;
import com.groupfour.MedicalCare.Utill.EmailUniqueness;
import com.groupfour.MedicalCare.Utill.PasswordCheck;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Service
public class LekarService {
    private static LekarRepository lekarRepository;
    private static KlinikaRepository klinikaRepository;
    private static AdminKlinikeRepository adminKlinikeRepository;
    private static Logger logger = LoggerFactory.getLogger(LekarService.class);

    @Autowired
    public LekarService(LekarRepository lRepository, KlinikaRepository kRepository,
                        AdminKlinikeRepository adKlinikeRepository) {
        lekarRepository = lRepository;
        klinikaRepository = kRepository;
        adminKlinikeRepository = adKlinikeRepository;
    }

    public static ResponseEntity<?> getLekareDTO(HttpSession session) {
        int klinikaId = nadjiIdKlinike(session);
        ArrayList<Lekar> lekari = lekarRepository.findAll();
        ArrayList<LekarDTO> lekariDTO = new ArrayList<>();

        for (Lekar lekar : lekari) {
            if (lekar.getKlinika().getId() == klinikaId && lekar.isAktivan()) {
                lekariDTO.add(mapirajLekara(lekar));
            }
        }
        return new ResponseEntity<>(lekariDTO, HttpStatus.OK);
    }

    public static int nadjiIdKlinike(HttpSession session){
        int idKlinike = -1;
        AdminKlinike adminKlinike = adminKlinikeRepository.findAdminKlinikeById((int) session.getAttribute("id"));
        // Samo adminKlinike moze pretrazivati lekare klinika
        if(adminKlinike != null)
        {
            try
            {
                return adminKlinike.getKlinika().getId();
            } catch (Exception e)
            {
                logger.error("Admin klinike nema kliniku");
                return idKlinike;
            }
        }
        logger.info("Admin klinike nije pronadjen");
        return idKlinike;
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

    public static ResponseEntity<?> dodavanjeNovogLekara(DodavanjeLekaraDTO dodavanjeLekaraDTO, HttpSession session){
        if(!EmailUniqueness.isEmailUniqe(dodavanjeLekaraDTO.getEmail())){
            return new ResponseEntity<>("Lekar sa datom mejl adresom vec postoji", HttpStatus.BAD_REQUEST);
        }
        int idKlinike = nadjiIdKlinike(session);
        Lekar lekar = konstrukcijaObjekta(dodavanjeLekaraDTO, idKlinike);
        if(lekar != null)
        {
            lekarRepository.save(lekar);
            return new ResponseEntity<>("Uspesno dodavanje novog lekara.", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Neuspesno dodavanje novog lekara.", HttpStatus.NOT_FOUND);
    }

    public static Lekar konstrukcijaObjekta(DodavanjeLekaraDTO dodavanjeLekaraDTO, int idKlinike){
        Klinika klinika = klinikaRepository.findById(idKlinike);
        if(klinika != null)
        {
            return Lekar.builder().ime(dodavanjeLekaraDTO.getIme()).prezime(dodavanjeLekaraDTO.getPrezime()).lozinka(PasswordCheck.hash(dodavanjeLekaraDTO.getLozinka())).email(dodavanjeLekaraDTO.getEmail()).aktivan(true).klinika(klinika).build();
        }
        logger.error("Klinika nije pronadjena!");
        return null;
    }

}
