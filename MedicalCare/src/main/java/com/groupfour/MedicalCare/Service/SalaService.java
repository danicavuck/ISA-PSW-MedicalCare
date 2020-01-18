package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.Administrator.AdminKlinike;
import com.groupfour.MedicalCare.Model.DTO.SalaDodavanjeDTO;
import com.groupfour.MedicalCare.Model.DTO.SalaPretragaDTO;
import com.groupfour.MedicalCare.Model.Klinika.Klinika;
import com.groupfour.MedicalCare.Model.Klinika.Sala;
import com.groupfour.MedicalCare.Repository.AdminKlinikeRepository;
import com.groupfour.MedicalCare.Repository.KlinikaRepository;
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

@Service
public class SalaService {
    private static SalaRepository salaRepository;
    private static AdminKlinikeRepository adminKlinikeRepository;
    private static KlinikaRepository klinikaRepository;
    private static Logger logger = LoggerFactory.getLogger(SalaService.class);

    @Autowired
    public SalaService(SalaRepository sRepository, KlinikaRepository kRepository,
                       AdminKlinikeRepository adkRepository) {
        salaRepository = sRepository;
        klinikaRepository = kRepository;
        adminKlinikeRepository = adkRepository;
    }

    public static ArrayList<SalaPretragaDTO> getSale(HttpSession session) {
        ArrayList<Sala> sale = salaRepository.findAll();
        Klinika klinika = nadjiKlinikuZaAdminaKlinike(session);
        if(klinika != null)
        {
            return vratiSveSaleZaOdgovarajucuKliniku(sale, klinika.getId());
        }
        return null;
    }

    private static Klinika nadjiKlinikuZaAdminaKlinike(HttpSession session){
        logger.info("ID: " + session.getAttribute("id"));
        AdminKlinike adminKlinike = adminKlinikeRepository.findAdminKlinikeById((int)session.getAttribute("id"));
        if(adminKlinike != null)
        {
            return adminKlinike.getKlinika();
        }
        logger.error("Admin klinike nije pronadjen");
        return null;
    }

    public static void deleteSala(SalaPretragaDTO salaPretragaDTO) {
        Sala sala = salaRepository.findByNazivSale(salaPretragaDTO.getNazivSale());
        setujAktivnostSaleNaNulu(sala);
        salaRepository.save(sala);
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
}
