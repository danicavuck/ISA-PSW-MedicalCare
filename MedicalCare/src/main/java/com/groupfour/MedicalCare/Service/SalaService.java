package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.DTO.SalaDodavanjeDTO;
import com.groupfour.MedicalCare.Model.DTO.SalaPretragaDTO;
import com.groupfour.MedicalCare.Model.Klinika.Klinika;
import com.groupfour.MedicalCare.Model.Klinika.Sala;
import com.groupfour.MedicalCare.Repository.KlinikaRepository;
import com.groupfour.MedicalCare.Repository.SalaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    public static ArrayList<SalaPretragaDTO> getSale(Integer klinikaId){
        ArrayList<Sala> sale = salaRepository.findAll();
        ArrayList<SalaPretragaDTO> saleDTO = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
        System.out.println("ID Klinike: " + klinikaId);
        if(klinikaId == 0){
            for(Sala s : sale){
                if(s.isAktivna()) {
                    saleDTO.add(mapper.map(s, SalaPretragaDTO.class));
                }
            }
            return saleDTO;
        } else{
            for(Sala s : sale){
                if(s.isAktivna() && s.getKlinika().getId() == klinikaId) {
                    saleDTO.add(mapper.map(s, SalaPretragaDTO.class));
                }
            }
            return saleDTO;
        }
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
        salaRepository.save(sala);
    }

    public static void addSala(SalaDodavanjeDTO salaDodavanjeDTO){
        Klinika klinika = klinikaRepository.findById(salaDodavanjeDTO.getKlinika());
        if(klinika == null){
            System.out.println("Klinika nije pronadjena (id): " + salaDodavanjeDTO.getKlinika());
            return;
        }
        Sala sala = Sala.builder().brojSale(salaDodavanjeDTO.getBrojSale()).aktivna(true).build();

        System.out.println("Dodata sala " + sala.getBrojSale() + " u kliniku " + klinika.getNaziv());

        klinika.dodajSalu(sala);
        klinikaRepository.save(klinika);


    }

    public static SalaPretragaDTO salaSearch(SalaPretragaDTO salaPretragaDTO){
        Sala sala = salaRepository.findByBrojSale(salaPretragaDTO.getBrojSale());
        ArrayList<SalaPretragaDTO> salaPretrage = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        if(sala != null){
            return modelMapper.map(sala, SalaPretragaDTO.class);
        }
        return null;
    }
}
