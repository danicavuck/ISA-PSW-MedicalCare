package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.DTO.PregledDTO;
import com.groupfour.MedicalCare.Model.Klinika.Sala;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Model.Pregled.Pregled;
import com.groupfour.MedicalCare.Model.Pregled.TipPregleda;
import com.groupfour.MedicalCare.Repository.LekarRepository;
import com.groupfour.MedicalCare.Repository.PregeldRepository;
import com.groupfour.MedicalCare.Repository.SalaRepository;
import com.groupfour.MedicalCare.Repository.TipPregledaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Set;

@Service
public class PregledService {
    private static PregeldRepository pregledRepository;
    private static TipPregledaRepository tipPregledaRepository;
    private static SalaRepository salaRepository;
    private static LekarRepository lekarRepository;

    @Autowired
    public PregledService(PregeldRepository pRepository, TipPregledaRepository tpRepository,
                          SalaRepository sRepository, LekarRepository lRepository) {
        pregledRepository = pRepository;
        tipPregledaRepository = tpRepository;
        salaRepository = sRepository;
        lekarRepository = lRepository;
    }

    public static ArrayList<PregledDTO> dobaviSvePreglede() {
        ArrayList<Pregled> pregledi = pregledRepository.findAll();
        ArrayList<PregledDTO> preglediDTO = new ArrayList<>();

        for (Pregled p : pregledi) {
            preglediDTO.add(mapirajPregledDTO(p));
        }

        return preglediDTO;
    }

    public static ArrayList<PregledDTO> dobaviPregledeZaKliniku(Integer klinikaId) {
        ArrayList<Pregled> pregledi = pregledRepository.findAll();
        ArrayList<PregledDTO> preglediDTO = new ArrayList<>();

        for (Pregled p : pregledi) {
            if (p.getSala().getKlinika().getId() == klinikaId)
                preglediDTO.add(mapirajPregledDTO(p));
        }

        return preglediDTO;
    }

    public static PregledDTO mapirajPregledDTO(Pregled pregled) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
        PregledDTO pregledDTO =
                PregledDTO.builder().trajanjePregleda(pregled.getTrajanjePregleda()).cena(pregled.getCena()).popust(pregled.getPopust()).sala(pregled.getSala().getBrojSale()).tipPregleda(pregled.getTipPregleda().getTipPregleda()).build();
        Set<Lekar> lekari = pregled.getLekari();
        Lekar lekar = lekari.iterator().next();
        LocalDateTime pocetakTermina = pregled.getTerminPregleda();
        long trajanje = (long) pregled.getTrajanjePregleda();
        LocalDateTime krajPregleda = pocetakTermina.plusMinutes(trajanje);

        pregledDTO.setLekar(lekar.getId());
        pregledDTO.setPocetakTermina(pocetakTermina.format(formatter));
        pregledDTO.setKrajTermina(krajPregleda.format(formatter));
        pregledDTO.setLekarImeIPrezime(lekar.getIme() + " " + lekar.getPrezime());

        return pregledDTO;
    }

    public static void kreirajNoviPregled(PregledDTO pregledDTO) {
        TipPregleda tipPregleda = tipPregledaRepository.findByTipPregleda(pregledDTO.getTipPregleda());
        Sala sala = salaRepository.findByBrojSale(pregledDTO.getSala());
        Lekar lekar = lekarRepository.findById(pregledDTO.getLekar());
        int popust = pregledDTO.getPopust();
        int cena = pregledDTO.getCena();
        int trajanje = pregledDTO.getTrajanjePregleda();
        LocalDateTime datumVreme = pregledDTO.getDatumVreme();

        Pregled pregled = Pregled.builder().aktivan(true).cena(cena).popust(popust).sala(sala).tipPregleda(tipPregleda).trajanjePregleda(trajanje).terminPregleda(datumVreme).build();

        System.out.println(pregled);

        // Rucno cu cuvati entitete jer Cascading nije cuvao polja za pregled kada sam ga dodavao kroz lekara
        pregledRepository.save(pregled);
        lekar.dodajPregled(pregled);
        lekarRepository.save(lekar);

    }

}
