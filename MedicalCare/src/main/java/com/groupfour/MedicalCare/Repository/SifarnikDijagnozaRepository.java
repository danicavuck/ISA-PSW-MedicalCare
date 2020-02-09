package com.groupfour.MedicalCare.Repository;

import com.groupfour.MedicalCare.Model.Dokumenti.SifarnikDijagnoza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface SifarnikDijagnozaRepository extends JpaRepository<SifarnikDijagnoza,Integer> {
    SifarnikDijagnoza findByKodBolesti(String kod);

    SifarnikDijagnoza findSifarnikDijagnozaById(int id);
}
