package com.groupfour.MedicalCare.Service;


import com.groupfour.MedicalCare.Model.Administrator.AdminKlinickogCentra;
import com.groupfour.MedicalCare.Model.Administrator.AdminKlinike;
import com.groupfour.MedicalCare.Model.DTO.*;
import com.groupfour.MedicalCare.Model.Klinika.Klinika;
import com.groupfour.MedicalCare.Model.Klinika.Sala;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Model.Osoblje.MedicinskaSestra;
import com.groupfour.MedicalCare.Model.Pregled.Pregled;
import com.groupfour.MedicalCare.Repository.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class KlinikaService {
    private static KlinikaRepository klinikaRepository;
    private static LekarRepository lekarRepository;
    private static MedicinskaSestraRepository medicinskaSestraRepository;
    private static AdminKlinikeRepository adminKlinikeRepository;
    private static AdminKCRepository adminKCRepository;
    private static SalaRepository salaRepository;
    private static PregledRepository pregledRepository;
    private static Logger logger = LoggerFactory.getLogger(KlinikaService.class);

    @Autowired
    public KlinikaService(KlinikaRepository kRepository, LekarRepository lRepo,
                          AdminKlinikeRepository aklinikeRepository,AdminKCRepository akcRepo,
                          MedicinskaSestraRepository msRepo,SalaRepository sRepo, PregledRepository pRepository) {
        klinikaRepository = kRepository;
        lekarRepository = lRepo;
        adminKlinikeRepository = aklinikeRepository;
        adminKCRepository = akcRepo;
        medicinskaSestraRepository = msRepo;
        salaRepository = sRepo;
        pregledRepository = pRepository;
    }


    public static ArrayList<KlinikaDTO> getKlinike(HttpSession session) {
        AdminKlinike adminKlinike = adminKlinikeRepository.findAdminKlinikeById((int) session.getAttribute("id"));
        ArrayList<KlinikaDTO> klinikeDTO = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
           if(adminKlinike == null)
        {
            logger.error("Nije pronadjen admin klinike");
            return null;
        }

        klinikeDTO.add(mapper.map(adminKlinike.getKlinika(), KlinikaDTO.class));
        return klinikeDTO;
    }


    public static ArrayList<KlinikaSveDTO> getKlinikeSve(HttpSession session) {
        AdminKlinickogCentra adminKlinickogCentra = adminKCRepository.findAdminKlinickogCentraById((int)session.getAttribute("id"));
        ArrayList<KlinikaSveDTO> klinikeDTO = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
        if(adminKlinickogCentra == null)
        {
            logger.error("Nije pronadjen admin klinickog centra");
            return null;
        }

        for(Klinika k : klinikaRepository.findAll()){
            klinikeDTO.add(mapper.map(k,KlinikaSveDTO.class));
        }

        return klinikeDTO;
    }

    public static ResponseEntity<?> updateKlinika(KlinikaDTO klinikaDTO) {
        Klinika klinika = klinikaRepository.findById(klinikaDTO.getId());
        if (klinika == null) {
            return new ResponseEntity<>("Neuspesna modifikacija", HttpStatus.BAD_REQUEST);
        }

        klinika.setNaziv(klinikaDTO.getNaziv());
        klinika.setAdresa(klinikaDTO.getAdresa());
        klinika.setOpis(klinikaDTO.getOpis());

        klinikaRepository.save(klinika);
        return new ResponseEntity<>("Uspesna modifikacija", HttpStatus.OK);
    }

    public static List<LekarDTO> getLekari(HttpSession session) {
        AdminKlinickogCentra admin = adminKCRepository.findAdminKlinickogCentraById((int)session.getAttribute("id"));

        if(admin == null)
        {
            logger.error("Nije pronadjen admin klinickog centra");
            return null;
        }
        List<Lekar> lekari = lekarRepository.findAll();
        List<LekarDTO> temp = new ArrayList<LekarDTO>();
        ModelMapper mapper = new ModelMapper();
        for (Lekar l : lekari) {
            if(l.getKlinika() == null)
                temp.add(mapiranjeLekara(l));
        }
        return temp;
    }
    public static List<MedSestraSveDTO> getSestre(HttpSession session) {
        AdminKlinickogCentra admin = adminKCRepository.findAdminKlinickogCentraById((int)session.getAttribute("id"));

        if(admin == null)
        {
            logger.error("Nije pronadjen admin klinickog centra");
            return null;
        }
        List<MedicinskaSestra> sestre = medicinskaSestraRepository.findAll();
        List<MedSestraSveDTO> temp = new ArrayList<MedSestraSveDTO>();
        ModelMapper mapper = new ModelMapper();
        for (MedicinskaSestra m : sestre) {
            if(m.getKlinika() == null)
                temp.add(mapiranjeSestre(m));
        }
        return temp;
    }

    public static List<AdminKlinikeSveDTO> getAdminiKlinike(HttpSession session) {
        AdminKlinickogCentra admin = adminKCRepository.findAdminKlinickogCentraById((int)session.getAttribute("id"));

        if(admin == null)
        {
            logger.error("Nije pronadjen admin klinickog centra");
            return null;
        }
        List<AdminKlinike> admini = adminKlinikeRepository.findAll();
        List<AdminKlinikeSveDTO> temp = new ArrayList<AdminKlinikeSveDTO>();
        ModelMapper mapper = new ModelMapper();
        for (AdminKlinike a : admini) {
            if(a.getKlinika() == null)
                temp.add(mapiranjeAdmina(a));
        }
        return temp;
    }

    public static List<SalaSveDTO> getSale(HttpSession session) {
        AdminKlinickogCentra admin = adminKCRepository.findAdminKlinickogCentraById((int)session.getAttribute("id"));

        if(admin == null)
        {
            logger.error("Nije pronadjen admin klinickog centra");
            return null;
        }
        List<Sala> sale = salaRepository.findAll();
        List<SalaSveDTO> temp = new ArrayList<SalaSveDTO>();
        ModelMapper mapper = new ModelMapper();
        for (Sala s : sale) {
            if(s.getKlinika() == null)
                temp.add(mapiranjeSale(s));
        }
        return temp;
    }


    public static LekarDTO mapiranjeLekara(Lekar lekar) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(lekar, LekarDTO.class);
    }

    public static MedSestraSveDTO mapiranjeSestre(MedicinskaSestra medicinskaSestra) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(medicinskaSestra, MedSestraSveDTO.class);
    }

    public static AdminKlinikeSveDTO mapiranjeAdmina(AdminKlinike adminKlinike) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(adminKlinike, AdminKlinikeSveDTO.class);
    }
    public static SalaSveDTO mapiranjeSale(Sala sala) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(sala, SalaSveDTO.class);
    }

    public static ResponseEntity<?> prihodiKlinike(PrihodDTO prihodDTO, HttpSession session){
        Klinika klinika = dobaviKlinikuIzSesije(session);
        if(klinika != null)
        {
            ArrayList<Pregled> preglediKlinike = dobaviPregledeKlinike(klinika);
            KlinikaPrihodiDTO klinikaPrihodiDTO = sumaCenaZaPeriod(preglediKlinike, prihodDTO);
            return new ResponseEntity<>(klinikaPrihodiDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public static Klinika dobaviKlinikuIzSesije(HttpSession session){
        AdminKlinike adminKlinike = adminKlinikeRepository.findAdminKlinikeById((int) session.getAttribute("id"));
        return adminKlinike.getKlinika();
    }

    public static ArrayList<Pregled> dobaviPregledeKlinike(Klinika klinika){
        ArrayList<Pregled> pregledi = pregledRepository.findAll();
        ArrayList<Pregled> odabraniPregledi = new ArrayList<>();
        for(Pregled pregled : pregledi){
            if(pregled.getSala().getKlinika().getId() == klinika.getId())
                odabraniPregledi.add(pregled);
        }
        return odabraniPregledi;
    }

    public static KlinikaPrihodiDTO sumaCenaZaPeriod(ArrayList<Pregled> pregledi, PrihodDTO prihodDTO){
        LocalDate pocetakIntervala = prihodDTO.getDatumVreme()[0];
        LocalDate krajIntervala = prihodDTO.getDatumVreme()[1];
        int suma = 0;
        int brojPregleda = 0;
        for(Pregled pregled : pregledi){
            if((pregled.getTerminPregleda().toLocalDate().isAfter(pocetakIntervala) || pregled.getTerminPregleda().toLocalDate().isEqual(pocetakIntervala)) && (pregled.getTerminPregleda().toLocalDate().isBefore(krajIntervala) || pregled.getTerminPregleda().toLocalDate().isEqual(krajIntervala))) {
                suma += pregled.getCena();
                brojPregleda++;
            }
        }
        return KlinikaPrihodiDTO.builder().profit(suma).brojPregleda(brojPregleda).build();
    }

    public static ResponseEntity<?> lokacijaKlinike(HttpSession session) {
        if(session.getAttribute("role").equals("adminklinike"))
        {
            AdminKlinike adminKlinike = adminKlinikeRepository.findAdminKlinikeById((int) session.getAttribute("id"));
            Klinika klinika = adminKlinike.getKlinika();
            KlinikaLokacijaDTO klinikaDTO =
                    KlinikaLokacijaDTO.builder().longitude(klinika.getLongitude()).latitude(klinika.getLatitude()).build();
            return new ResponseEntity<>(klinikaDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<?> azurirajLokacijuKlinike(HttpSession session, KlinikaLokacijaDTO klinikaDTO){
        AdminKlinike adminKlinike = adminKlinikeRepository.findAdminKlinikeById((int) session.getAttribute("id"));
        Klinika klinika = adminKlinike.getKlinika();
        if(klinika != null)
        {
            klinika.setLongitude(klinikaDTO.getLongitude());
            klinika.setLatitude(klinikaDTO.getLatitude());
            klinikaRepository.save(klinika);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
