package com.groupfour.MedicalCare.Repository;

import com.groupfour.MedicalCare.Model.Pregled.PreglediNaCekanju;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface PreglediNaCekanjuRepository extends JpaRepository<PreglediNaCekanju, Integer> {
    ArrayList<PreglediNaCekanju> findAll();
    PreglediNaCekanju getPregledNaCekanjuById(Integer id);
    ArrayList<PreglediNaCekanju> getPreglediNaCekanjuByKlinikaId(Integer klinikaId);
}
