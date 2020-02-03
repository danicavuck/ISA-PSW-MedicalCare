package com.groupfour.MedicalCare.Repository;

import com.groupfour.MedicalCare.Model.Dokumenti.SifarnikDijagnoza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SifarnikDijagnozaRepository extends JpaRepository<SifarnikDijagnoza,Integer> {

    public SifarnikDijagnoza findByKodBolesti(String kod);
    SifarnikDijagnoza findSifarnikDijagnozaById(int id);
}
