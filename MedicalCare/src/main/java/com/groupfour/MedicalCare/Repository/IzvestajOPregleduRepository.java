package com.groupfour.MedicalCare.Repository;

import com.groupfour.MedicalCare.Model.Dokumenti.IzvestajOPregledu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface IzvestajOPregleduRepository extends JpaRepository<IzvestajOPregledu,Integer> {
    IzvestajOPregledu findIzvestajOPregleduById(int id);
}
