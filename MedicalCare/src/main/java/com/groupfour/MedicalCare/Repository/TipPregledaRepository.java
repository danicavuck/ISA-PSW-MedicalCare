package com.groupfour.MedicalCare.Repository;

import com.groupfour.MedicalCare.Model.Pregled.TipPregleda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
public interface TipPregledaRepository extends JpaRepository<TipPregleda, Integer> {
    ArrayList<TipPregleda> findAll();
    ArrayList<TipPregleda> findTipPregledaByKlinikaId(int klinikaId);
    TipPregleda findByTipPregleda(String tipPregleda);
    TipPregleda findById(int id);
}
