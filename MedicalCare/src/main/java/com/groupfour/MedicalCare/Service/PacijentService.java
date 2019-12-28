package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;
import com.groupfour.MedicalCare.Model.Tabele.PacijentKlinika;
import com.groupfour.MedicalCare.Repository.PacijentRepository;
import com.groupfour.MedicalCare.Utill.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PacijentService{

    private static PacijentRepository pacijentRepository;

    @Autowired
    public PacijentService(PacijentRepository repo) {
        pacijentRepository = repo;
    }


    public static ResponseEntity<?> dobaviPacijenteOdgovarajuceKlinike(Integer klinikaId){
        ArrayList<PacijentKlinika.Key> pacijentiKlinike = new ArrayList<>();
        ArrayList<Pacijent> pacijenti = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        if (session.isOpen()) {
            session.beginTransaction();

            Query query = session.createQuery("select key from pacijent_klinika where id_klinike = " + klinikaId);
            System.out.println(query.getResultList());
            pacijentiKlinike = (ArrayList<PacijentKlinika.Key>) query.getResultList();

            pacijenti = dobaviPacijenteIzTabele(pacijentiKlinike);

            session.getTransaction().commit();
            session.close();
        } else {
            System.out.println("Couldn't open connection with database");
        }

        return new ResponseEntity<>(pacijenti, HttpStatus.OK);
    }

    public static ArrayList<Pacijent> dobaviPacijenteIzTabele(ArrayList<PacijentKlinika.Key> pacijentKlinikaKey){
        ArrayList<Pacijent> pacijenti = new ArrayList<>();

        for(PacijentKlinika.Key key : pacijentKlinikaKey){
            Pacijent pacijent = pacijentRepository.findPacijentById(key.getId_pacijent());
            if(pacijent != null){
                pacijenti.add(pacijent);
            }
            else {
                System.out.println("Nije nasao pacijenta za dati ID" + key.getId_pacijent());
                return null;
            }
        }

        return pacijenti;
    }
}
