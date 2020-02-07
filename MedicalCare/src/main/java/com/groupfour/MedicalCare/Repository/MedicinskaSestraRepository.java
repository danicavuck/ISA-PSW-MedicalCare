package com.groupfour.MedicalCare.Repository;

import com.groupfour.MedicalCare.Model.Osoblje.MedicinskaSestra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface MedicinskaSestraRepository extends JpaRepository<MedicinskaSestra, Integer> {
    MedicinskaSestra findMedicinskaSestraByEmail(String email);
    MedicinskaSestra findMedicinskaSestraById(int id);
}
