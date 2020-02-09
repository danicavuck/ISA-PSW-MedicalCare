package com.groupfour.MedicalCare.Repository;

import com.groupfour.MedicalCare.Model.Dokumenti.Karton;
import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KartonRepository extends JpaRepository<Karton,Integer> {

    Karton findKartonById(int id);
    Karton findKartonByPacijent(Pacijent pacijent);
}
