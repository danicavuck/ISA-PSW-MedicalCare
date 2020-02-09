package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.Administrator.AdminKlinickogCentra;
import com.groupfour.MedicalCare.Model.Administrator.AdminKlinike;
import com.groupfour.MedicalCare.Model.DTO.PacijentDetailsDTO;
import com.groupfour.MedicalCare.Model.Klinika.Klinika;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Model.Osoblje.MedicinskaSestra;
import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;
import com.groupfour.MedicalCare.Model.Tabele.PacijentKlinika;
import com.groupfour.MedicalCare.Repository.*;
import com.groupfour.MedicalCare.Utill.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Service
public class PacijentService{
    private static PacijentRepository pacijentRepository;
    private static LekarRepository lekarRepository;
    private static AdminKlinikeRepository adminKlinikeRepository;
    private static AdminKCRepository adminKCRepository;
    private static MedicinskaSestraRepository medicinskaSestraRepository;
    private static Logger logger = LoggerFactory.getLogger(PacijentService.class);

    @Autowired
    public PacijentService(PacijentRepository repo, LekarRepository lRepository,
                           AdminKlinikeRepository adminKlinikeRepo, AdminKCRepository adminKCRepo,
                           MedicinskaSestraRepository medSestraRepository) {
        pacijentRepository = repo;
        lekarRepository = lRepository;
        adminKlinikeRepository = adminKlinikeRepo;
        adminKCRepository = adminKCRepo;
        medicinskaSestraRepository = medSestraRepository;
    }

    public static ResponseEntity<?> dobaviPacijente(HttpSession session)
    {
        if(session.getAttribute("role") != null)
        {
            switch ((String)session.getAttribute("role"))
            {
                case "lekar": return dobaviPacijenteZaLekara(session);
                case "adminklinike" : return dobaviPacijenteZaAdminaKlinike(session);
                case "adminkc" : return dobaviPacijenteZaAdminaKlinickogCentra(session);
                case "med_sestra" : return dobaviPacijenteZaMedicinskuSestru(session);
                default: new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }


    public static ResponseEntity<?> dobaviPacijenteZaLekara(HttpSession session)
    {
        Lekar lekar = lekarRepository.findLekarById((int) session.getAttribute("id"));


        if(lekar != null)
        {
            int idKlinike = 0;
            try
            {
                 idKlinike = lekar.getKlinika().getId();
                return dobaviPacijenteOdgovarajuceKlinike(idKlinike);
            } catch (Exception e)
            {
                logger.info("Lekar nema nijednu kliniku.");
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        }


        logger.info("Nije pronadjen lekar.");
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    public static ResponseEntity<?> dobaviPacijenteZaAdminaKlinike(HttpSession session)
    {
        AdminKlinike adminKlinike = adminKlinikeRepository.findAdminKlinikeById((int) session.getAttribute("id"));
        if(adminKlinike != null)
        {
            int idKlinike = 0;
            try
            {
                idKlinike = adminKlinike.getKlinika().getId();
                return dobaviPacijenteOdgovarajuceKlinike(idKlinike);
            } catch (Exception e)
            {
                logger.info("Admin klinike nema nijednu kliniku.");
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        }
        logger.info("Nije pronadjen admin klinike.");
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    // Admin Klinickog centra ce imati pristup svim pacijentima svih klinika, jer drugacije nije navedeno
    public static ResponseEntity<?> dobaviPacijenteZaAdminaKlinickogCentra(HttpSession session)
    {
        AdminKlinickogCentra adminKlinickogCentra =
                adminKCRepository.findAdminKlinickogCentraById((int) session.getAttribute("id"));

        if(adminKlinickogCentra != null)
        {
            return dobaviPacijenteSvihKlinika();
        }
        logger.info("Nije pronadjen admin klinickog centra.");
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<?> dobaviPacijenteZaMedicinskuSestru(HttpSession session)
    {
        MedicinskaSestra medicinskaSestra =
                medicinskaSestraRepository.findMedicinskaSestraById((int) session.getAttribute("id"));
        if(medicinskaSestra != null)
        {
            int idKlinike = 0;
            try
            {
                idKlinike = medicinskaSestra.getKlinika().getId();
                return dobaviPacijenteOdgovarajuceKlinike(idKlinike);
            } catch (Exception e)
            {
                logger.info("Medicinska sestra nema nijednu kliniku.");
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        }
        logger.info("Nije pronadjena medicinska sestra.");
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<?> dobaviPacijenteSvihKlinika(){
        ArrayList<PacijentKlinika.Key> pacijentiKlinike = new ArrayList<>();
        ArrayList<Pacijent> pacijenti = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        if (session.isOpen()) {
            session.beginTransaction();
            Query query = session.createQuery("select key from pacijent_klinika where id_klinike > " + 0);
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

    public static ResponseEntity<?> dobaviPacijenteOdgovarajuceKlinike(Integer klinikaId){
        ArrayList<PacijentKlinika.Key> pacijentiKlinike = new ArrayList<>();
        ArrayList<Pacijent> pacijenti = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        if (session.isOpen()) {
            session.beginTransaction();
            Query query = session.createQuery("select key from pacijent_klinika where id_klinike = " + klinikaId);
            pacijentiKlinike = (ArrayList<PacijentKlinika.Key>) query.getResultList();
            System.out.println(pacijentiKlinike.size());
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
            System.out.println(key);
            Pacijent pacijent = pacijentRepository.findPacijentById(key.getId_pacijent());

            System.out.println(pacijent.getIme());
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

    // Nije dobra praksa, ali necu ovde proveravati da li pacijent pripada istoj klinici kojoj pripada i osoblje koje
    // potrazuje informacije jer u startu dobavljam informacije o pacijentima samo klinike kojoj pripada osoblje
    public static ResponseEntity<?> dobaviPacijenta(Integer pacijentId){
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

    // I ovde vazi da se ne proverava da li je lekar.klinika.id == pacijent.klinika.id
    public static ResponseEntity<?> dobaviPacijentaZaImeIPrezime(String kriterijum) {
        ArrayList<Pacijent> pacijenti = new ArrayList<>();
        kriterijum = kriterijum.trim();
        try
        {
            String ime = "", prezime = "";
            String[] ime_prezime = kriterijum.split(" ");
            ime = ime_prezime[0];
            prezime = ime_prezime[1];

            pacijenti = pacijentRepository.findPacijentByImeAndPrezime(ime, prezime);
            if(pacijenti.size() != 0) {
                return new ResponseEntity<>(pacijenti, HttpStatus.OK);
            }
        } catch (Exception e)
        {
            pacijenti = pronadjiPacijentaPoBrojuOsiguranja(kriterijum);
            return new ResponseEntity<>(pacijenti, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public static ArrayList<Pacijent> pronadjiPacijentaPoBrojuOsiguranja(String brojOsiguranja) {
        ArrayList<Pacijent> pacijenti = new ArrayList<>();
        Pacijent pacijent = pacijentRepository.findPacijentByBrojOsiguranja(brojOsiguranja);
        pacijenti.add(pacijent);
        return pacijenti;
    }
}
