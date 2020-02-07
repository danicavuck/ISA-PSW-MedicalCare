package com.groupfour.MedicalCare.Repository;

import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Transactional
public interface LekarRepository extends JpaRepository<Lekar, Integer> {
    Lekar findLekarByEmail(String email);
    Lekar findLekarById(int id);
    ArrayList<Lekar> findAll();
}
