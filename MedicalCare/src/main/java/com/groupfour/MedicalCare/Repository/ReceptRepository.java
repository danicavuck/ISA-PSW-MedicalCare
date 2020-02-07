package com.groupfour.MedicalCare.Repository;

import com.groupfour.MedicalCare.Model.Dokumenti.Recept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ReceptRepository extends JpaRepository<Recept,Integer> {

    @Override
    List<Recept> findAll();
}
