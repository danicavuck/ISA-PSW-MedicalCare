package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.Administrator.AdminKlinike;
import com.groupfour.MedicalCare.Model.DTO.DodavanjeLekaraDTO;
import com.groupfour.MedicalCare.Model.DTO.LekarDTO;
import com.groupfour.MedicalCare.Model.DTO.LekarIzmenaPodatakaDTO;
import com.groupfour.MedicalCare.Model.DTO.LekarKalendarDTO;
import com.groupfour.MedicalCare.Model.Klinika.Klinika;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Model.Pregled.Operacija;
import com.groupfour.MedicalCare.Model.Pregled.Pregled;
import com.groupfour.MedicalCare.Model.Zahtevi.OdsustvoLekara;
import com.groupfour.MedicalCare.Repository.AdminKlinikeRepository;
import com.groupfour.MedicalCare.Repository.KlinikaRepository;
import com.groupfour.MedicalCare.Repository.LekarRepository;
import com.groupfour.MedicalCare.Utill.EmailUniqueness;
import com.groupfour.MedicalCare.Utill.PasswordCheck;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Set;

@Service
public class LekarService {
    private static LekarRepository lekarRepository;
    private static KlinikaRepository klinikaRepository;
    private static AdminKlinikeRepository adminKlinikeRepository;
    private static Logger logger = LoggerFactory.getLogger(LekarService.class);

    @Autowired
    public LekarService(LekarRepository lRepository, KlinikaRepository kRepository,
                        AdminKlinikeRepository adKlinikeRepository) {
        lekarRepository = lRepository;
        klinikaRepository = kRepository;
        adminKlinikeRepository = adKlinikeRepository;
    }

    public static ResponseEntity<?> getLekareDTO(HttpSession session) {
        int klinikaId = nadjiIdKlinike(session);
        ArrayList<Lekar> lekari = lekarRepository.findAll();
        ArrayList<LekarDTO> lekariDTO = new ArrayList<>();

        for (Lekar lekar : lekari) {
            if (lekar.getKlinika().getId() == klinikaId && lekar.isAktivan()) {
                lekariDTO.add(mapirajLekara(lekar));
            }
        }
        return new ResponseEntity<>(lekariDTO, HttpStatus.OK);
    }

    public static int nadjiIdKlinike(HttpSession session){
        int idKlinike = -1;
        AdminKlinike adminKlinike = adminKlinikeRepository.findAdminKlinikeById((int) session.getAttribute("id"));
        // Samo adminKlinike moze pretrazivati lekare klinika
        if(adminKlinike != null)
        {
            try
            {
                return adminKlinike.getKlinika().getId();
            } catch (Exception e)
            {
                logger.error("Admin klinike nema kliniku");
                return idKlinike;
            }
        }
        logger.info("Admin klinike nije pronadjen");
        return idKlinike;
    }

    public static LekarDTO mapirajLekara(Lekar lekar) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(lekar, LekarDTO.class);
    }


    public static ResponseEntity<?> brisanjeLekara(LekarDTO lekarDTO){
        Lekar lekar = lekarRepository.findLekarById(lekarDTO.getId());
        if(lekar != null){
            if(lekarImaAktivnePreglede(lekar))
                return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

            lekar.setAktivan(false);
            lekarRepository.save(lekar);
            return new ResponseEntity<>("Uspesno izbrisan lekar", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("Neuspesno brisanje lekara", HttpStatus.BAD_REQUEST);
    }

    public static boolean lekarImaAktivnePreglede(Lekar lekar){
        Set<Pregled> pregledi = lekar.getSetPregleda();
        for(Pregled pregled : pregledi) {
            if(pregled.isAktivan())
                return true;
        }
        return false;
    }

    @Transactional
    public static ResponseEntity<?> dodavanjeNovogLekara(DodavanjeLekaraDTO dodavanjeLekaraDTO, HttpSession session){
        if(!EmailUniqueness.isEmailUniqe(dodavanjeLekaraDTO.getEmail())){
            return new ResponseEntity<>("Lekar sa datom mejl adresom vec postoji", HttpStatus.BAD_REQUEST);
        }
        int idKlinike = nadjiIdKlinike(session);
        Lekar lekar = konstrukcijaObjekta(dodavanjeLekaraDTO, idKlinike);
        if(lekar != null)
        {
            lekarRepository.save(lekar);
            return new ResponseEntity<>("Uspesno dodavanje novog lekara.", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Neuspesno dodavanje novog lekara.", HttpStatus.NOT_FOUND);
    }

    public static Lekar konstrukcijaObjekta(DodavanjeLekaraDTO dodavanjeLekaraDTO, int idKlinike){
        Klinika klinika = klinikaRepository.findById(idKlinike);
        if(klinika != null)
        {
            Lekar lekar =
                    Lekar.builder().ime(dodavanjeLekaraDTO.getIme()).prezime(dodavanjeLekaraDTO.getPrezime()).lozinka(PasswordCheck.hash(dodavanjeLekaraDTO.getLozinka())).email(dodavanjeLekaraDTO.getEmail()).aktivan(true).klinika(klinika).prvoLogovanje(true).build();
            try {
                String pocetak = dodavanjeLekaraDTO.getRadnoVreme();
                String[] parsiranje = pocetak.split("-");
                int pocetakTermina = Integer.parseInt(parsiranje[0]);
                int krajTermina = Integer.parseInt(parsiranje[1]);
                lekar.setPocetakRadnogVremena(pocetakTermina);
                lekar.setKrajRadnogVremena(krajTermina);
            } catch (Exception e)
            {
                logger.error("Neuspesno parsiranje radnog vremena");
                return null;
            }
            return  lekar;
        }
        logger.error("Klinika nije pronadjena!");
        return null;
    }

    public static ResponseEntity<?> izmenaLicnihPodataka(LekarIzmenaPodatakaDTO lekarIzmenaPodatakaDTO,
                                                         HttpSession session){
        Lekar lekar = lekarRepository.findLekarById((int) session.getAttribute("id"));
        if(lekar != null)
        {
            izmeniPodatke(lekar, lekarIzmenaPodatakaDTO);
            lekarRepository.save(lekar);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public static void izmeniPodatke(Lekar lekar, LekarIzmenaPodatakaDTO lekarIzmenaPodatakaDTO)
    {
        if(!lekarIzmenaPodatakaDTO.getIme().equals(""))
            lekar.setIme(lekarIzmenaPodatakaDTO.getIme());
        if(!lekarIzmenaPodatakaDTO.getPrezime().equals(""))
            lekar.setPrezime(lekarIzmenaPodatakaDTO.getPrezime());
        if(!lekarIzmenaPodatakaDTO.getEmail().equals(""))
            lekar.setEmail(lekarIzmenaPodatakaDTO.getEmail());
        if(trebaIzmenitiLozinku(lekar, lekarIzmenaPodatakaDTO))
            lekar.setLozinka(PasswordCheck.hash(lekarIzmenaPodatakaDTO.getNovaLozinka()));
    }



    private static boolean trebaIzmenitiLozinku(Lekar lekar, LekarIzmenaPodatakaDTO lekarIzmenaPodatakaDTO) {
        boolean zadovoljavajuce = false;
        if(!lekarIzmenaPodatakaDTO.getNovaLozinka().equals("") && PasswordCheck.verifyHash(lekarIzmenaPodatakaDTO.getStaraLozinka() ,
                lekar.getLozinka()))
        {
            zadovoljavajuce = true;
        }
        return zadovoljavajuce;
    }

    public static ResponseEntity<?> preglediIOperacijeZaRadniKalendar(HttpSession session) {
        Lekar lekar = lekarRepository.findLekarById((int) session.getAttribute("id"));
        if(lekar != null)
        {
            Set<Pregled> pregledi = lekar.getSetPregleda();
            Set<Operacija> operacije = lekar.getListaOperacija();
            Set<OdsustvoLekara> odsustvaLekara = lekar.getListaOdsusta();
            ArrayList<LekarKalendarDTO> lekarKalendarDTOS = new ArrayList<>();

            for(Pregled pregled : pregledi) {
                if(pregled.isAktivan())
                {
                        LekarKalendarDTO lekarKalendarDTO = new LekarKalendarDTO();
                        lekarKalendarDTO.setStart(pregled.getTerminPregleda());
                        lekarKalendarDTO.setEnd(pregled.getTerminPregleda().plusMinutes(pregled.getTrajanjePregleda()));
                        lekarKalendarDTO.setTitle(pregled.getTipPregleda().getTipPregleda());
                        lekarKalendarDTOS.add(lekarKalendarDTO);
                }
            }

            for(Operacija operacija : operacije) {
                if(operacija.isAktivan())
                {
                    LekarKalendarDTO lekarKalendarDTO = new LekarKalendarDTO();
                    lekarKalendarDTO.setStart(operacija.getTerminOperacije());
                    lekarKalendarDTO.setEnd(operacija.getTerminOperacije().plusMinutes(operacija.getTrajanjeOperacije()));
                    lekarKalendarDTO.setTitle("Operacija");
                    lekarKalendarDTOS.add(lekarKalendarDTO);
                }
            }
            for(OdsustvoLekara odsustvo : odsustvaLekara){
                if(odsustvo.isAktivno() && odsustvo.isOdobren())
                {
                    LekarKalendarDTO lekarKalendarDTO = new LekarKalendarDTO();
                    lekarKalendarDTO.setStart(odsustvo.getPocetakOdsustva());
                    lekarKalendarDTO.setEnd(odsustvo.getKrajOdsustva());
                    lekarKalendarDTO.setTitle("Odsustvo");
                    lekarKalendarDTOS.add(lekarKalendarDTO);
                }
            }

            return new ResponseEntity<>(lekarKalendarDTOS, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<?> detaljiOLekaru(HttpSession session) {
        Lekar lekar = lekarRepository.findLekarById((int) session.getAttribute("id"));
        if(lekar != null)
        {
            LekarIzmenaPodatakaDTO izmenaPodatakaDTO =
                    LekarIzmenaPodatakaDTO.builder().ime(lekar.getIme()).prezime(lekar.getPrezime()).email(lekar.getEmail()).build();
            return new ResponseEntity<>(izmenaPodatakaDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

}
