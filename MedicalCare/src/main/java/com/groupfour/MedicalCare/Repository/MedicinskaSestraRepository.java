package com.groupfour.MedicalCare.Repository;

import com.groupfour.MedicalCare.Model.Osoblje.MedicinskaSestra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicinskaSestraRepository extends JpaRepository<MedicinskaSestra, Integer> {
    MedicinskaSestra findMedicinskaSestraByEmail(String email);
    MedicinskaSestra findMedicinskaSestraById(int id);
}
