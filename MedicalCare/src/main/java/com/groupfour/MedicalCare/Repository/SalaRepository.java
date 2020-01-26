package com.groupfour.MedicalCare.Repository;

import com.groupfour.MedicalCare.Model.Klinika.Klinika;
import com.groupfour.MedicalCare.Model.Klinika.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Integer> {
    ArrayList<Sala> findAll();
    Sala findByNazivSale(String nazivSale);
    Sala findById(int id);
}
