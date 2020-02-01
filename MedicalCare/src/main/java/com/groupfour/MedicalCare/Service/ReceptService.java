package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.DTO.ReceptDTO;
import com.groupfour.MedicalCare.Model.Dokumenti.Recept;
import com.groupfour.MedicalCare.Model.Osoblje.MedicinskaSestra;
import com.groupfour.MedicalCare.Repository.MedicinskaSestraRepository;
import com.groupfour.MedicalCare.Repository.ReceptRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OptimisticLockException;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReceptService {

    public static ReceptRepository receptRepository;
    public static MedicinskaSestraRepository medicinskaSestraRepository;

    @Autowired
    public ReceptService(ReceptRepository rRepo , MedicinskaSestraRepository mRepo) {
        receptRepository  = rRepo;
        medicinskaSestraRepository = mRepo;
    }

    public List<ReceptDTO> getAllActive() {
        List<ReceptDTO> temp = new ArrayList<>();
        List<Recept> all = receptRepository.findAll();
        //System.out.println(all.size());
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).isAktivan()) {
                //System.out.println(all.get(i).getIme());
                temp.add(mapiranjeRecepta(all.get(i)));
            }
        }

        return temp;

    }

    @Transactional(readOnly = false)
    public void overiRecept(Integer id, HttpSession session) {
        MedicinskaSestra medicinskaSestra = medicinskaSestraRepository.findMedicinskaSestraById((int) session.getAttribute("id"));

        if(medicinskaSestra != null){
            try {
                Recept recept = receptRepository.getOne(id);
                recept.setOvereno(true);
                recept.setAktivan(false);
                recept.setMedicinskaSestra(medicinskaSestra);

                receptRepository.save(recept);
            } catch (OptimisticLockException e) {
                System.out.println("exception");
            }

        }
    }


    public static ReceptDTO mapiranjeRecepta(Recept recept){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(recept,ReceptDTO.class);
    }


}
