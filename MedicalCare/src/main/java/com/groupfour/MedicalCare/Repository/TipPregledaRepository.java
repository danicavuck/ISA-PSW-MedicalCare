package com.groupfour.MedicalCare.Repository;

import com.groupfour.MedicalCare.Model.Pregled.TipPregleda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface TipPregledaRepository extends JpaRepository<TipPregleda, Integer> {
    ArrayList<TipPregleda> findAll();
    ArrayList<TipPregleda> findTipPregledaByKlinikaId(int klinikaId);
    TipPregleda findByTipPregleda(String tipPregleda);
}
