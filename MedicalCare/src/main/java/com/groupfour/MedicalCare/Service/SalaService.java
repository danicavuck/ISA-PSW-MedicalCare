package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.DTO.SalaDodavanjeDTO;
import com.groupfour.MedicalCare.Model.DTO.SalaPretragaDTO;
import com.groupfour.MedicalCare.Model.HibernateUtil;
import com.groupfour.MedicalCare.Model.Klinika.Klinika;
import com.groupfour.MedicalCare.Model.Klinika.Sala;
import com.groupfour.MedicalCare.Repository.KlinikaRepository;
import com.groupfour.MedicalCare.Repository.SalaRepository;
import org.hibernate.Session;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SalaService {
    private static SalaRepository salaRepository;
    private static KlinikaRepository klinikaRepository;

    @Autowired
    public SalaService(SalaRepository sRepository, KlinikaRepository kRepository){
        salaRepository = sRepository;
        klinikaRepository = kRepository;
    }

    public static ArrayList<SalaPretragaDTO> getSale(){
        ArrayList<Sala> sale = salaRepository.findAll();
        ArrayList<SalaPretragaDTO> saleDTO = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();

        for(Sala s : sale){
            saleDTO.add(mapper.map(s, SalaPretragaDTO.class));
        }

        return saleDTO;
    }

    public static void deleteSala(SalaPretragaDTO salaPretragaDTO){
        Sala sala = salaRepository.findByBrojSale(salaPretragaDTO.getBrojSale());
        if(sala == null){
            System.out.println(salaPretragaDTO);
            System.out.println("Sala " + salaPretragaDTO.getBrojSale() + " nije pronadjena");
        }

        try {
            sala.setAktivna(false);
        } catch (Exception e){
            System.out.println("Neuspesno setovanje aktivnosti na 0");
        }
        // cuvanje promenjene vrednosti
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.update(sala);
        session.getTransaction().commit();
        session.close();
    }

    public static void addSala(SalaDodavanjeDTO salaDodavanjeDTO){
        Klinika klinika = klinikaRepository.findById(salaDodavanjeDTO.getKlinika());
        if(klinika == null){
            System.out.println("Klinika nije pronadjena (id): " + salaDodavanjeDTO.getKlinika());
            return;
        }
        Sala sala = Sala.builder().brojSale(salaDodavanjeDTO.getBrojSale()).aktivna(true).build();

        klinika.dodajSalu(sala);

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.update(klinika);
        session.getTransaction().commit();
        session.close();
    }
}
