package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.DTO.SalaDodavanjeDTO;
import com.groupfour.MedicalCare.Model.DTO.SalaPretragaDTO;
import com.groupfour.MedicalCare.Model.Klinika.Klinika;
import com.groupfour.MedicalCare.Model.Klinika.Sala;
import com.groupfour.MedicalCare.Repository.KlinikaRepository;
import com.groupfour.MedicalCare.Repository.SalaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Service
public class SalaService {
    private static SalaRepository salaRepository;
    private static KlinikaRepository klinikaRepository;

    @Autowired
    public SalaService(SalaRepository sRepository, KlinikaRepository kRepository) {
        salaRepository = sRepository;
        klinikaRepository = kRepository;
    }

    public static ArrayList<SalaPretragaDTO> getSale(Integer klinikaId) {
        ArrayList<Sala> sale = salaRepository.findAll();
        return vratiSveSaleZaOdgovarajucuKliniku(sale, klinikaId);
    }

    public static void deleteSala(SalaPretragaDTO salaPretragaDTO) {
        Sala sala = salaRepository.findByBrojSale(salaPretragaDTO.getBrojSale());
        setujAktivnostSaleNaNulu(sala);
        salaRepository.save(sala);
    }

    public static void addSala(SalaDodavanjeDTO salaDodavanjeDTO) {
        Klinika klinika = klinikaRepository.findById(salaDodavanjeDTO.getKlinika());
        dodavanjeNoveSaleUKliniku(klinika, salaDodavanjeDTO);
        klinikaRepository.save(klinika);
    }

    // Helper funkcije

    private static void formatiranjeDatumaSala(Sala sala, SalaPretragaDTO salaDTO) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
        salaDTO.setPocetakTermina(sala.getPocetakTermina().format(formatter));
        salaDTO.setKrajTermina(sala.getKrajTermina().format(formatter));
    }

    public static ArrayList<SalaPretragaDTO> vratiSveSaleZaOdgovarajucuKliniku(ArrayList<Sala> sale, Integer klinikaID) {
        ArrayList<SalaPretragaDTO> saleDTO = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
        for (Sala s : sale) {
            if (s.isAktivna() && ((klinikaID == 0) || (s.getKlinika().getId() == klinikaID))) {
                saleDTO.add(mapper.map(s, SalaPretragaDTO.class));
                formatiranjeDatumaSala(s, saleDTO.get(saleDTO.size() - 1));
            }
        }
        return saleDTO;
    }

    public static ResponseEntity<?> pretraziSaluPoBrojuSale(SalaPretragaDTO salaPretragaDTO) {
        ArrayList<SalaPretragaDTO> saleDTO = new ArrayList<>();
        Sala sala = salaRepository.findByBrojSale(salaPretragaDTO.getBrojSale());
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
        Sala sala = Sala.builder().brojSale(salaDodavanjeDTO.getBrojSale()).aktivna(true).build();
        System.out.println("Dodata sala " + sala.getBrojSale() + " u kliniku " + klinika.getNaziv());
        klinika.dodajSalu(sala);
    }

    public static SalaPretragaDTO mapiranjeSaleNaSalaPretragaDTO(Sala sala) {
        ModelMapper modelMapper = new ModelMapper();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
        if (sala != null) {
            SalaPretragaDTO salaPretragaDTO = modelMapper.map(sala, SalaPretragaDTO.class);
            salaPretragaDTO.setPocetakTermina(sala.getPocetakTermina().format(formatter));
            salaPretragaDTO.setKrajTermina(sala.getKrajTermina().format(formatter));
            return salaPretragaDTO;
        }
        return null;
    }

    public static void setujAktivnostSaleNaNulu(Sala sala) {
        try {
            sala.setAktivna(false);
        } catch (NullPointerException e) {
            System.out.println("Sala " + sala.getBrojSale() + " nije pronadjena");
            System.out.println("Neuspesno setovanje aktivnosti na 0");
        }
    }
}
