package com.groupfour.MedicalCare.Repository;

import com.groupfour.MedicalCare.Model.Klinika.Sala;
import com.groupfour.MedicalCare.Model.Pregled.Operacija;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Repository
@Transactional
public interface OperacijaRepository extends JpaRepository<Operacija, Integer> {
    ArrayList<Operacija> findAll();
    ArrayList<Operacija> findOperacijaBySala(Sala sala);
    Operacija findOperacijaById(int id);
}
