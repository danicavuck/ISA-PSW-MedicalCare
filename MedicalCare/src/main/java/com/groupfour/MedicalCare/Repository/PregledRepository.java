package com.groupfour.MedicalCare.Repository;

import com.groupfour.MedicalCare.Model.Klinika.Sala;
import com.groupfour.MedicalCare.Model.Pregled.Pregled;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Repository
@Transactional
public interface PregledRepository extends JpaRepository<Pregled, Integer> {
    ArrayList<Pregled> findAll();
    ArrayList<Pregled> findPregledBySala(Sala sala);
    Pregled findPregledById(int id);
}
