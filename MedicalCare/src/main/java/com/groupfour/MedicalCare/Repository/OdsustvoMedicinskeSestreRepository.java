package com.groupfour.MedicalCare.Repository;

import com.groupfour.MedicalCare.Model.Zahtevi.OdsustvoMedicinskeSestre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OdsustvoMedicinskeSestreRepository extends JpaRepository<OdsustvoMedicinskeSestre,Integer> {
}
