package com.groupfour.MedicalCare.Repository;

import com.groupfour.MedicalCare.Model.Administrator.AdminKlinike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Transactional
public interface AdminKlinikeRepository extends JpaRepository<AdminKlinike, Integer> {
    AdminKlinike findAdminKlinikeByEmail(String email);
    AdminKlinike findAdminKlinikeById(int id);
    ArrayList<AdminKlinike> findAll();
}
