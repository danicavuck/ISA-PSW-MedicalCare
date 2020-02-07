package com.groupfour.MedicalCare.Repository;

import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Transactional
public interface PacijentRepository extends JpaRepository<Pacijent, Integer> {
    Pacijent findUserByEmail(String email);
    Pacijent findPacijentById(Integer id);
    ArrayList<Pacijent> findPacijentByImeAndPrezime(String ime, String prezime);
    Pacijent findPacijentByBrojOsiguranja(String brojOsiguranja);
}
