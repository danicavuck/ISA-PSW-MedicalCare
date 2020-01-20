package com.groupfour.MedicalCare.Repository;

import com.groupfour.MedicalCare.Model.Administrator.AdminKlinickogCentra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminKCRepository extends JpaRepository<AdminKlinickogCentra, Integer> {
    AdminKlinickogCentra findAdminKlinickogCentraByEmail(String email);
    AdminKlinickogCentra findAdminKlinickogCentraById(int id);
}
