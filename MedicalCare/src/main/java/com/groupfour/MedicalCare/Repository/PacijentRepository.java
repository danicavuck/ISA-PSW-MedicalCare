package com.groupfour.MedicalCare.Repository;

import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacijentRepository extends JpaRepository<Pacijent, Integer> {
    Pacijent findUserByEmail(String email);
}
