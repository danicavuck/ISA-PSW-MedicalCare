package com.groupfour.MedicalCare.Repository;

import com.groupfour.MedicalCare.Model.Pregled.TipPregleda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface TipPregledaRepository extends JpaRepository<TipPregleda, Integer> {
    ArrayList<TipPregleda> findAll();

    TipPregleda findByTipPregleda(String tipPregleda);
}
