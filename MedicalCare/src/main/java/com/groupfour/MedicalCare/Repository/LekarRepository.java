package com.groupfour.MedicalCare.Repository;

import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LekarRepository extends JpaRepository<Lekar, Integer> {
    Lekar findLekarByEmail(String email);
}
