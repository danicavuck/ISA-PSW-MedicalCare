package com.groupfour.MedicalCare.Repository;

import com.groupfour.MedicalCare.Model.Klinika.Klinika;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface KlinikaRepository extends JpaRepository<Klinika, Integer> {
    ArrayList<Klinika> findAll();
}
