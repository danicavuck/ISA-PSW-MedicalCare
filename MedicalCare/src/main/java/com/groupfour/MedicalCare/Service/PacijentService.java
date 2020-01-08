package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.DTO.PacijentDTO;
import com.groupfour.MedicalCare.Model.DTO.PacijentDetailsDTO;
import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;
import com.groupfour.MedicalCare.Model.Tabele.PacijentKlinika;
import com.groupfour.MedicalCare.Repository.PacijentRepository;
import com.groupfour.MedicalCare.Utill.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.modelmapper.ModelMapper;
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
    public static ResponseEntity<?> dobaviKonkretnogPacijenta(Integer pacijentId){
        Pacijent pacijent = pacijentRepository.findPacijentById(pacijentId);
        PacijentDetailsDTO pacijentDetailsDTO;
        if(pacijent != null){
            pacijentDetailsDTO = mapiranjePacijenta(pacijent);
            return new ResponseEntity<>(pacijentDetailsDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public static PacijentDetailsDTO mapiranjePacijenta(Pacijent pacijent){
        ModelMapper mapper = new ModelMapper();
        try {
            return mapper.map(pacijent, PacijentDetailsDTO.class);
        } catch (Exception e){
            System.out.println("Neuspesno mapiranje objekta");
            e.printStackTrace();
        }
        return null;
    }

    public static ResponseEntity<?> dobaviPacijentaZaImeIPrezime(String imeIPrezime) {
        ArrayList<Pacijent> pacijenti = new ArrayList<>();
        imeIPrezime = imeIPrezime.trim();
        String ime = "", prezime = "";
        String[] ime_prezime = imeIPrezime.split(" ");
        // Za testiranje u Postmanu parisraj String
//        ime = ime_prezime[0].substring(1);
//        prezime = ime_prezime[1].substring(0, ime_prezime[1].length()-1);
        ime = ime_prezime[0];
        prezime = ime_prezime[1];

        pacijenti = pacijentRepository.findPacijentByImeAndPrezime(ime, prezime);
        if(pacijenti.size() != 0) {
            return new ResponseEntity<>(pacijenti, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
