package com.groupfour.MedicalCare.Repository;

import com.groupfour.MedicalCare.Model.Dokumenti.SifarnikLekova;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface SifarnikLekovaRepository extends JpaRepository<SifarnikLekova,Integer> {
    SifarnikLekova findByKodLeka(String kod);
    SifarnikLekova findSifarnikLekovaById(int id);

}
