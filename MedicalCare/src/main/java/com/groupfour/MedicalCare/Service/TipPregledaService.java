package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.Administrator.AdminKlinike;
import com.groupfour.MedicalCare.Model.DTO.PregledDTO;
import com.groupfour.MedicalCare.Model.DTO.TipPregledaDTO;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Model.Osoblje.MedicinskaSestra;
import com.groupfour.MedicalCare.Model.Pregled.Pregled;
import com.groupfour.MedicalCare.Model.Pregled.PreglediNaCekanju;
import com.groupfour.MedicalCare.Model.Pregled.TipPregleda;
import com.groupfour.MedicalCare.Repository.*;
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
public class TipPregledaService {
    private static TipPregledaRepository tipPregledaRepository;
    private static AdminKlinikeRepository adminKlinikeRepository;
    private static LekarRepository lekarRepository;
    private static MedicinskaSestraRepository medicinskaSestraRepository;
    private static PregledRepository pregledRepository;
    private static PreglediNaCekanjuRepository preglediNaCekanjuRepository;
    private static Logger logger = LoggerFactory.getLogger(TipPregledaService.class);

    @Autowired
    public TipPregledaService(TipPregledaRepository tRepo, AdminKlinikeRepository akRepository,
                              LekarRepository lRepository, MedicinskaSestraRepository mSestraRepository,
                              PregledRepository pRepository, PreglediNaCekanjuRepository pNaCekanjuRepository) {
        tipPregledaRepository = tRepo;
        adminKlinikeRepository = akRepository;
        lekarRepository = lRepository;
        medicinskaSestraRepository = mSestraRepository;
        pregledRepository = pRepository;
        preglediNaCekanjuRepository = pNaCekanjuRepository;
    }

    public static ResponseEntity<?> vratiTipovePregleda(HttpSession session) {
        int klinikaId = nadjiIdKlinike(session);
        if(klinikaId != -1)
        {
            ArrayList<TipPregleda> tipPregleda = tipPregledaRepository.findTipPregledaByKlinikaId(klinikaId);
            return new ResponseEntity<>(mapirajTipovePregleda(tipPregleda), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public static int nadjiIdKlinike(HttpSession session){
        switch ((String) session.getAttribute("role"))
        {
            case "lekar": return idKlinikeLekar(session);
            case "adminklinike" : return idKlinikeAdminKlinike(session);
            case "med_sestra" : return idKlinikeMedicinskaSestra(session);
            default: return -1;
        }
    }

    public static int idKlinikeLekar(HttpSession session){
        int idKlinike = -1;
        Lekar lekar = lekarRepository.findLekarById((int) session.getAttribute("id"));
        if(lekar != null)
        {
            try
            {
                idKlinike = lekar.getKlinika().getId();
                return idKlinike;
            } catch (Exception e)
            {
                logger.error("Lekar nema kliniku");
            }
        }
        logger.error("Lekar nije pronadjen");
        return idKlinike;
    }

    public static int idKlinikeAdminKlinike(HttpSession session){
        int idKlinike = -1;
        AdminKlinike adminKlinike = adminKlinikeRepository.findAdminKlinikeById((int) session.getAttribute("id"));
        if(adminKlinike != null)
        {
            try
            {
                idKlinike = adminKlinike.getKlinika().getId();
                return idKlinike;
            } catch (Exception e)
            {
                logger.error("Admin klinike nema kliniku");
            }
        }
        logger.error("Admin klinike nije pronadjen");
        return idKlinike;
    }

    public static int idKlinikeMedicinskaSestra(HttpSession session){
        int idKlinike = -1;
        MedicinskaSestra medicinskaSestra = medicinskaSestraRepository.findMedicinskaSestraById((int) session.getAttribute("id"));
        if(medicinskaSestra != null)
        {
            try
            {
                idKlinike = medicinskaSestra.getKlinika().getId();
                return idKlinike;
            } catch (Exception e)
            {
                logger.error("Medicinska sestra nema kliniku");
            }
        }
        logger.error("Medicinska sestra nije pronadjena");
        return idKlinike;
    }


    public static ArrayList<TipPregledaDTO> mapirajTipovePregleda(ArrayList<TipPregleda> tipPregleda) {
        ArrayList<TipPregledaDTO> tipPregledaDTO = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        for (TipPregleda tp : tipPregleda) {
            if (tp.isAktivan()) {
                tipPregledaDTO.add(modelMapper.map(tp, TipPregledaDTO.class));
            }
        }
        return tipPregledaDTO;
    }

    public static ResponseEntity<?> dodajNoviTipPregleda(TipPregledaDTO tipPregledaDTO, HttpSession session) {
        int klinikaId = vratiIDKlinike(session);
        if(klinikaId != -1)
        {
            TipPregleda tipPregleda =
                    TipPregleda.builder().tipPregleda(tipPregledaDTO.getTipPregleda()).aktivan(true).predefinisan(true).klinikaId(klinikaId).build();
            tipPregledaRepository.save(tipPregleda);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    public static int vratiIDKlinike(HttpSession session){
        int klinikaId = -1;
        AdminKlinike adminKlinike = adminKlinikeRepository.findAdminKlinikeById((int) session.getAttribute("id"));
        if(adminKlinike != null)
        {
            try
            {
                klinikaId = adminKlinike.getKlinika().getId();
                return klinikaId;
            } catch (Exception e)
            {
                logger.error("Admin klinike nema kliniku");
            }
        }
        logger.info("Nije pronadjen admin klinike");
        return klinikaId;
    }

    public static ResponseEntity<?> azurirajTipPregleda(TipPregledaDTO tipPregledaDTO){
        TipPregleda tipPregleda = tipPregledaRepository.findById(tipPregledaDTO.getId());
        if(tipPregleda != null)
        {
            if(dozvoljenoBrisanjeIliModifikacija(tipPregleda))
            {
                if(!tipPregledaDTO.getTipPregleda().equals(""))
                {
                    tipPregleda.setTipPregleda(tipPregledaDTO.getTipPregleda());
                    tipPregledaRepository.save(tipPregleda);
                    return new ResponseEntity<>(null, HttpStatus.OK);
                }
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public static boolean dozvoljenoBrisanjeIliModifikacija(TipPregleda tipPregleda){
        ArrayList<Pregled> pregledi = pregledRepository.findAll();
        for(Pregled p : pregledi)
        {
            if(p.getTipPregleda().getId() == tipPregleda.getId() && p.isAktivan())
                return false;
        }
        ArrayList<PreglediNaCekanju> preglediNaCekanju = preglediNaCekanjuRepository.findAll();
        for(PreglediNaCekanju p : preglediNaCekanju)
        {
            if(p.getTipPregleda().getId() == tipPregleda.getId() && p.isAktivan())
                return false;
        }
        return true;
    }

    public static ResponseEntity<?> obrisiTipPregleda(TipPregledaDTO tipPregledaDTO){
        TipPregleda tipPregleda = tipPregledaRepository.findById(tipPregledaDTO.getId());
        if(tipPregleda != null)
        {
            if(dozvoljenoBrisanjeIliModifikacija(tipPregleda))
            {
                tipPregleda.setAktivan(false);
                tipPregledaRepository.save(tipPregleda);
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

}
