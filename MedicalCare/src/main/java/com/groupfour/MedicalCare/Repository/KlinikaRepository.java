package com.groupfour.MedicalCare.Repository;

import com.groupfour.MedicalCare.Model.Klinika.Klinika;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Transactional
public interface KlinikaRepository extends JpaRepository<Klinika, Integer> {
    ArrayList<Klinika> findAll();
    Klinika findByNaziv(String naziv);
    Klinika findById(int id);
}
