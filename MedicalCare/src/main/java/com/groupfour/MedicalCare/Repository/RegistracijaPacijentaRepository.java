package com.groupfour.MedicalCare.Repository;

import com.groupfour.MedicalCare.Model.Zahtevi.RegistracijaPacijenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface RegistracijaPacijentaRepository extends JpaRepository<RegistracijaPacijenta, Integer> {
    @Override
    List<RegistracijaPacijenta> findAll();
}
