package com.groupfour.MedicalCare.Repository;

import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface LekarRepository extends JpaRepository<Lekar, Integer> {
    Lekar findByEmail(String email);

    Lekar findById(int id);

    ArrayList<Lekar> findAll();

}
