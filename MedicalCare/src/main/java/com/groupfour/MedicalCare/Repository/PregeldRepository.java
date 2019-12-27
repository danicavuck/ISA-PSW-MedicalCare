package com.groupfour.MedicalCare.Repository;

import com.groupfour.MedicalCare.Model.Pregled.Pregled;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface PregeldRepository extends JpaRepository<Pregled, Integer> {
    ArrayList<Pregled> findAll();
}
