package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.DTO.ReceptDTO;
import com.groupfour.MedicalCare.Model.Dokumenti.Recept;
import com.groupfour.MedicalCare.Model.Osoblje.MedicinskaSestra;
import com.groupfour.MedicalCare.Repository.LekarRepository;
import com.groupfour.MedicalCare.Repository.MedicinskaSestraRepository;
import com.groupfour.MedicalCare.Repository.ReceptRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public static LekarRepository lekarRepository;
    private static Logger logger = LoggerFactory.getLogger(MedicinskaSestra.class);


    @Autowired
    public ReceptService(ReceptRepository rRepo , MedicinskaSestraRepository mRepo,LekarRepository lRepo) {
        receptRepository  = rRepo;
        medicinskaSestraRepository = mRepo;
        lekarRepository = lRepo;
    }

    public List getAllActive(HttpSession session) {
        MedicinskaSestra medicinskaSestra = medicinskaSestraRepository.findMedicinskaSestraById((int) session.getAttribute("id"));

        if(medicinskaSestra == null ){
            logger.error("Nije pronadjena medicinska sestra");
            return null;
        }
        //int idKlinike = medicinskaSestra.getKlinika().getId();
        List<ReceptDTO> temp = new ArrayList<>();
        List<Recept> all = receptRepository.findAll();

        for (int i = 0; i < all.size(); i++) {
            int idKlinike = all.get(i).getLekar().getKlinika().getId();

            if (all.get(i).isAktivan() && !all.get(i).isOvereno() &&  medicinskaSestra.getKlinika().getId() == idKlinike){
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
