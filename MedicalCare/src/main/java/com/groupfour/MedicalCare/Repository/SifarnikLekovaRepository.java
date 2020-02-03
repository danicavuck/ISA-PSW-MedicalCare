package com.groupfour.MedicalCare.Repository;

import com.groupfour.MedicalCare.Model.Dokumenti.SifarnikLekova;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SifarnikLekovaRepository extends JpaRepository<SifarnikLekova,Integer> {
    public SifarnikLekova findByKodLeka(String kod);
    SifarnikLekova findSifarnikLekovaById(int id);
}
