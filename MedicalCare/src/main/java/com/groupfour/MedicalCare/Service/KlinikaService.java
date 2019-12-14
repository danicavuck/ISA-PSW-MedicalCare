package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.Administrator.AdminKlinike;
import com.groupfour.MedicalCare.Model.DTO.KlinikaDTO;
import com.groupfour.MedicalCare.Model.HibernateUtil;
import com.groupfour.MedicalCare.Model.Klinika.Klinika;
import com.groupfour.MedicalCare.Model.Klinika.Sala;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Model.Osoblje.MedicinskaSestra;
import com.groupfour.MedicalCare.Repository.*;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KlinikaService {
    @Autowired
    private static KlinikaRepository klinikaRepository;
    @Autowired
    private LekarRepository lekarRepository;
    @Autowired
    private MedicinskaSestraRepository medicinskaSestraRepository;
    @Autowired
    private AdminKlinikeRepository adminKlinikeRepository;
    @Autowired
    private SalaRepository salaRepository;


    @Autowired
    public KlinikaService(KlinikaRepository kRepository){
        klinikaRepository = kRepository;
    }

    public static ArrayList<Klinika> getKlinike(){
        return klinikaRepository.findAll();
    }

    public List<Lekar> getLekari(){return lekarRepository.findAll();}

    public List<MedicinskaSestra> getMedicinskeSestre(){ return medicinskaSestraRepository.findAll();}

    public List<AdminKlinike> getAdminiKlinike(){ return adminKlinikeRepository.findAll();}

    public List<Sala> getSale(){return salaRepository.findAll();}

    public ResponseEntity<String> registerKlinika(KlinikaDTO klinika){
        Klinika temp = klinikaRepository.findByNaziv(klinika.getNaziv());
        if(temp == null){
            temp = Klinika.builder().naziv(klinika.getNaziv()).adresa(klinika.getAdresa()).opis(klinika.getOpis()).listaLekara(klinika.getLekari()).listaSestara(klinika.getSestre()).spisakSala(klinika.getSale()).adminiKlinike(klinika.getAdmini()).build();

            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            if(session.isOpen()){
                session.beginTransaction();
                session.save(temp);

                session.getTransaction().commit();
                session.close();

                return new ResponseEntity<>("Instance created", HttpStatus.CREATED);
            } else{
                System.out.println("Couldn't open connection with database");
                return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<>("Klinika sa tim nazivom vec postoji!", HttpStatus.FORBIDDEN);
    }

}
