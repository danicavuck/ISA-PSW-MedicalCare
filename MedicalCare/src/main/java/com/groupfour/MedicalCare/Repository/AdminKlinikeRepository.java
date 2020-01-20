package com.groupfour.MedicalCare.Repository;

import com.groupfour.MedicalCare.Model.Administrator.AdminKlinike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface AdminKlinikeRepository extends JpaRepository<AdminKlinike, Integer> {
    AdminKlinike findAdminKlinikeByEmail(String email);
    AdminKlinike findAdminKlinikeById(int id);
    ArrayList<AdminKlinike> findAll();
}
