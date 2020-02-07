package com.groupfour.MedicalCare.Repository;

import com.groupfour.MedicalCare.Model.Zahtevi.OdsustvoLekara;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Transactional
public interface OdsustvoLekaraRepository extends JpaRepository<OdsustvoLekara, Integer> {
    ArrayList<OdsustvoLekara> findAll();
    ArrayList<OdsustvoLekara> findOdsustvoByLekarId(Integer lekarId);
}
