package com.groupfour.MedicalCare.Repository;

import com.groupfour.MedicalCare.Model.Klinika.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface SalaRepository extends JpaRepository<Sala, Integer> {
    ArrayList<Sala> findAll();
    Sala findByBrojSale(int brojSale);
}
