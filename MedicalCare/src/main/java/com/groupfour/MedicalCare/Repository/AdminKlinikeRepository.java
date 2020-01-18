package com.groupfour.MedicalCare.Repository;

import com.groupfour.MedicalCare.Model.Administrator.AdminKlinike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminKlinikeRepository extends JpaRepository<AdminKlinike, Integer> {
    AdminKlinike findAdminKlinikeByEmail(String email);
    AdminKlinike findAdminKlinikeById(int id);
}
