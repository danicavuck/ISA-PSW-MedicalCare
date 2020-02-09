package com.groupfour.MedicalCare.Repository;

import com.groupfour.MedicalCare.Model.Zahtevi.OdsustvoMedicinskeSestre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface OdsustvoMedicinskeSestreRepository extends JpaRepository<OdsustvoMedicinskeSestre,Integer> {

}
