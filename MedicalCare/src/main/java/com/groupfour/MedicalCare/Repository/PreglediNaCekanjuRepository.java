package com.groupfour.MedicalCare.Repository;

import com.groupfour.MedicalCare.Model.Pregled.PreglediNaCekanju;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.ArrayList;

@Transactional
public interface PreglediNaCekanjuRepository extends JpaRepository<PreglediNaCekanju, Integer> {
    ArrayList<PreglediNaCekanju> findAll();
    PreglediNaCekanju getPregledNaCekanjuById(Integer id);
    @Lock(value = LockModeType.PESSIMISTIC_READ)
    ArrayList<PreglediNaCekanju> getPreglediNaCekanjuByKlinikaId(Integer klinikaId);
}
