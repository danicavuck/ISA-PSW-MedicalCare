package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.DTO.MedSestraIzmenaPodatakaDTO;
import com.groupfour.MedicalCare.Model.Osoblje.MedicinskaSestra;
import com.groupfour.MedicalCare.Repository.MedicinskaSestraRepository;
import com.groupfour.MedicalCare.Utill.PasswordCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class MedicinskaSestraService {
    private static MedicinskaSestraRepository medicinskaSestraRepository;

    @Autowired
    public MedicinskaSestraService(MedicinskaSestraRepository medSestraRepository){
        medicinskaSestraRepository = medSestraRepository;
    }

    public static ResponseEntity<?> azurirajPodatkeMedicinskeSestre(MedSestraIzmenaPodatakaDTO medSestraIzmenaPodatakaDTO, HttpSession session)
    {
        MedicinskaSestra medicinskaSestra =
                medicinskaSestraRepository.findMedicinskaSestraById((int) session.getAttribute("id"));
        if(medicinskaSestra != null)
        {
            if(!medSestraIzmenaPodatakaDTO.getEmail().equals(""))
                medicinskaSestra.setEmail(medSestraIzmenaPodatakaDTO.getEmail());
            if(!medSestraIzmenaPodatakaDTO.getIme().equals(""))
                medicinskaSestra.setIme(medSestraIzmenaPodatakaDTO.getIme());
            if(!medSestraIzmenaPodatakaDTO.getPrezime().equals(""))
                medicinskaSestra.setPrezime(medSestraIzmenaPodatakaDTO.getPrezime());
            if(trebaIzmenitiLozinku(medicinskaSestra, medSestraIzmenaPodatakaDTO))
                medicinskaSestra.setLozinka(PasswordCheck.hash(medSestraIzmenaPodatakaDTO.getNovaLozinka()));

            medicinskaSestraRepository.save(medicinskaSestra);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public static boolean trebaIzmenitiLozinku(MedicinskaSestra medicinskaSestra, MedSestraIzmenaPodatakaDTO dto){
        if(!dto.getNovaLozinka().equals("") && PasswordCheck.verifyHash(dto.getStaraLozinka(), medicinskaSestra.getLozinka()))
        {
            return true;
        }
        return false;
    }
}
