package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.Administrator.AdminKlinike;
import com.groupfour.MedicalCare.Model.DTO.SalaDodavanjeDTO;
import com.groupfour.MedicalCare.Model.DTO.SalaPretragaDTO;
import com.groupfour.MedicalCare.Model.DTO.SalaZauzeceDTO;
import com.groupfour.MedicalCare.Model.Klinika.Klinika;
import com.groupfour.MedicalCare.Model.Klinika.Sala;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Model.Pregled.Operacija;
import com.groupfour.MedicalCare.Model.Pregled.Pregled;
import com.groupfour.MedicalCare.Repository.AdminKlinikeRepository;
import com.groupfour.MedicalCare.Repository.KlinikaRepository;
import com.groupfour.MedicalCare.Repository.LekarRepository;
import com.groupfour.MedicalCare.Repository.SalaRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Set;

@Service
public class SalaService {
    private static SalaRepository salaRepository;
    private static AdminKlinikeRepository adminKlinikeRepository;
    private static KlinikaRepository klinikaRepository;
    private static LekarRepository lekarRepository;
    private static Logger logger = LoggerFactory.getLogger(SalaService.class);

    @Autowired
    public SalaService(SalaRepository sRepository, KlinikaRepository kRepository,
                       AdminKlinikeRepository adkRepository, LekarRepository lekarRepo) {
        salaRepository = sRepository;
        klinikaRepository = kRepository;
        adminKlinikeRepository = adkRepository;
        lekarRepository = lekarRepo;
    }

    public static ArrayList<SalaPretragaDTO> getSale(HttpSession session) {
        ArrayList<Sala> sale = salaRepository.findAll();
        Klinika klinika = null;
        switch ((String) session.getAttribute("role"))
        {
            case "adminklinike": klinika = nadjiKlinikuZaAdminaKlinike(session);
                break;
            case "lekar" : klinika = nadjiKlinikuZaLekaraKlinike(session);
                break;
            default: logger.error("Nije pronadjena rola korisnika sistema");
        }

        if(klinika != null)
        {
            return vratiSveSaleZaOdgovarajucuKliniku(sale, klinika.getId());
        }
        return null;
    }

    private static Klinika nadjiKlinikuZaAdminaKlinike(HttpSession session){
        AdminKlinike adminKlinike = adminKlinikeRepository.findAdminKlinikeById((int)session.getAttribute("id"));
        if(adminKlinike != null)
        {
            return adminKlinike.getKlinika();
        }
        logger.error("Admin klinike nije pronadjen");
        return null;
    }

    private static Klinika nadjiKlinikuZaLekaraKlinike(HttpSession session){
        Lekar lekar = lekarRepository.findLekarById((int)session.getAttribute("id"));
        if(lekar != null)
        {
            return lekar.getKlinika();
        }
        logger.error("Lekar klinike nije pronadjen");
        return null;
    }

    public static ResponseEntity<?> deleteSala(SalaPretragaDTO salaPretragaDTO) {
        if(dozvoljenoBrisanjeIliMenjanjeSale(salaPretragaDTO))
        {
            Sala sala = salaRepository.findByNazivSale(salaPretragaDTO.getNazivSale());
            setujAktivnostSaleNaNulu(sala);
            salaRepository.save(sala);
            return new ResponseEntity<>("Sala je uspesno obrisana", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }

    public static boolean dozvoljenoBrisanjeIliMenjanjeSale(SalaPretragaDTO salaPretragaDTO){
        Sala sala = salaRepository.findById(salaPretragaDTO.getId());
        if(sala != null)
        {
            Set<Pregled> pregledi = sala.getPregledi();
            for(Pregled pregled : pregledi)
            {
                if(pregled.isAktivan())
                    return false;
            }
            return true;
        }
        return false;
    }

    public static void addSala(SalaDodavanjeDTO salaDodavanjeDTO, HttpSession session) {
        Klinika klinika = informacijeOKlinici(session);
        if(klinika != null)
        {
            dodavanjeNoveSaleUKliniku(klinika, salaDodavanjeDTO);
            klinikaRepository.save(klinika);
        }
        else
        {
            logger.info("Sala nije dodata u kliniku jer klinika nije pronadjena");
        }
    }

    // Helper funkcije
    private static Klinika informacijeOKlinici(HttpSession session){
        String role = (String) session.getAttribute("role");
        AdminKlinike adminKlinike = null;
        if(role.equals("adminklinike"))
        {
            adminKlinike = adminKlinikeRepository.findAdminKlinikeById((int) session.getAttribute("id"));
            try
            {
                return adminKlinike.getKlinika();
            }catch (Exception e)
            {
                logger.error("Admin klinike ili ne postoji, ili nema kliniku!");
            }
        }
        return null;
    }

    public static ArrayList<SalaPretragaDTO> vratiSveSaleZaOdgovarajucuKliniku(ArrayList<Sala> sale, Integer klinikaID) {
        ArrayList<SalaPretragaDTO> saleDTO = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
        for (Sala s : sale) {
            if (s.isAktivna() && ((klinikaID == 0) || (s.getKlinika().getId() == klinikaID))) {
                saleDTO.add(mapper.map(s, SalaPretragaDTO.class));
            }
        }
        return saleDTO;
    }

    public static ResponseEntity<?> pretraziSaluPoBrojuSale(SalaPretragaDTO salaPretragaDTO) {
        ArrayList<SalaPretragaDTO> saleDTO = new ArrayList<>();
        Sala sala = salaRepository.findByNazivSale(salaPretragaDTO.getNazivSale());
        if(sala == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        saleDTO.add(mapiranjeSaleNaSalaPretragaDTO(sala));
        return new ResponseEntity<>(saleDTO, HttpStatus.OK);
    }

    public static void dodavanjeNoveSaleUKliniku(Klinika klinika, SalaDodavanjeDTO salaDodavanjeDTO) {
        if (klinika == null) {
            System.out.println("Klinika nije pronadjena (id): " + salaDodavanjeDTO.getKlinika());
            return;
        }
        Sala sala = Sala.builder().nazivSale(salaDodavanjeDTO.getNazivSale()).aktivna(true).build();
        klinika.dodajSalu(sala);
    }

    public static SalaPretragaDTO mapiranjeSaleNaSalaPretragaDTO(Sala sala) {
        ModelMapper modelMapper = new ModelMapper();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
        if (sala != null) {
            return modelMapper.map(sala, SalaPretragaDTO.class);
        }
        return null;
    }

    public static void setujAktivnostSaleNaNulu(Sala sala) {
        try {
            sala.setAktivna(false);
        } catch (NullPointerException e) {
            System.out.println("Sala " + sala.getNazivSale() + " nije pronadjena");
            System.out.println("Neuspesno setovanje aktivnosti na 0");
        }
    }

    public static ResponseEntity<?> azurirajPodatkeSale(SalaPretragaDTO salaPretragaDTO, HttpSession session){
        Sala sala = salaRepository.findById(salaPretragaDTO.getId());
        if(sala != null)
        {
            if(dozvoljenoBrisanjeIliMenjanjeSale(salaPretragaDTO))
            {
                if(!salaPretragaDTO.getNazivSale().equals(""))
                {
                    sala.setNazivSale(salaPretragaDTO.getNazivSale());
                    salaRepository.save(sala);
                }
                return new ResponseEntity<>(null, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<?> preglediIOperacijeZaSalu(int salaId) {
        Sala sala = salaRepository.findById(salaId);
        if(sala != null)
        {
            ArrayList<SalaZauzeceDTO> salaZauzeceDTO = new ArrayList<>();
            Set<Pregled> pregledi = sala.getPregledi();
            Set<Operacija> operacije = sala.getOperacije();
            SalaZauzeceDTO salaDTO = new SalaZauzeceDTO();

            for(Pregled pregled : pregledi) {
                if(pregled.isAktivan())
                {
                    salaDTO.setStart(pregled.getTerminPregleda());
                    salaDTO.setTitle(pregled.getTipPregleda().getTipPregleda());
                    salaZauzeceDTO.add(salaDTO);
                }
            }

            for(Operacija operacija : operacije) {
                if(operacija.isAktivan())
                {
                    salaDTO.setStart(operacija.getTerminOperacije());
                    salaDTO.setTitle("Operacija");
                    salaZauzeceDTO.add(salaDTO);
                }
            }

            return new ResponseEntity<>(salaZauzeceDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
