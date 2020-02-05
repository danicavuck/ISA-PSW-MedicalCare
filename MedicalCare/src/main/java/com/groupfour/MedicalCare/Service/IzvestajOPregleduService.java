package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.DTO.IzvestajOPregleduDTO;
import com.groupfour.MedicalCare.Model.Dokumenti.*;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;
import com.groupfour.MedicalCare.Repository.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class IzvestajOPregleduService {

    private static IzvestajOPregleduRepository izvestajOPregleduRepository;
    private static SifarnikLekovaRepository sifarnikLekovaRepository;
    private static SifarnikDijagnozaRepository sifarnikDijagnozaRepository;
    private static LekarRepository lekarRepository;
    private static PacijentRepository pacijentRepository;
    private static KartonRepository kartonRepository;
    private static ReceptRepository receptRepository;
    private static Logger logger = LoggerFactory.getLogger(Lekar.class);

    @Autowired
    public IzvestajOPregleduService(SifarnikDijagnozaRepository sdRepo,SifarnikLekovaRepository slRepo,LekarRepository lRepo,PacijentRepository pRepo,KartonRepository kRepo,IzvestajOPregleduRepository izRepo,ReceptRepository receptRepo){
        sifarnikDijagnozaRepository = sdRepo;
        sifarnikLekovaRepository = slRepo;
        lekarRepository = lRepo;
        pacijentRepository = pRepo;
        kartonRepository = kRepo;
        izvestajOPregleduRepository = izRepo;
        receptRepository = receptRepo;
    }

    public ResponseEntity<?> dodajIzvestajOPregledu(IzvestajOPregleduDTO izvestajOPregleduDTO, HttpSession session) {
        Lekar lekar = lekarRepository.findLekarById((int) session.getAttribute("id"));
        Pacijent pacijent = pacijentRepository.findPacijentById(izvestajOPregleduDTO.getIdPacijent());
        Karton karton = kartonRepository.findKartonByPacijent(pacijent);
        int[] id_lekova = izvestajOPregleduDTO.getIdLek();
        Set<SifarnikLekova> lekovi = new HashSet<SifarnikLekova>();
        Set<Recept> recepti = new HashSet<Recept>();

        if (lekar == null) {
            logger.error("Nije pronadjen lekar");
            return new ResponseEntity<>("Nije nadjen lekar", HttpStatus.UNAUTHORIZED);
        }
        //pravi se toliko recepata koliko je prepisano lekova pacijentu
        if(pacijent != null){
            for(int i  = 0 ; i< id_lekova.length ; i++){
                lekovi.add(sifarnikLekovaRepository.findSifarnikLekovaById(id_lekova[i]));
            }

            for(SifarnikLekova l : lekovi){
                Recept r = Recept.builder().idLeka(lekar.getId()).kodLeka(l.getKodLeka()).nazivLeka(l.getNazivLeka()).lekar(lekar).overeno(false).build();
                recepti.add(r);
                receptRepository.save(r);
            }
            SifarnikDijagnoza dijagnoza = sifarnikDijagnozaRepository.findSifarnikDijagnozaById(izvestajOPregleduDTO.getIdDijagnoza());
            IzvestajOPregledu izvestaj = IzvestajOPregledu.builder().aktivan(true).informacijeOPregledu(izvestajOPregleduDTO.getInformacijeOPregledu()).sifarnikDijagnoza(dijagnoza).recepti(recepti).lekar(lekar).build();
            izvestajOPregleduRepository.save(izvestaj);
            //izmene se upisuju u zdravstveni karton pacijenta
            karton.dodajDijagnozu(dijagnoza);
            karton.dodajIzvestaj(izvestaj);
            kartonRepository.save(karton);

            return new ResponseEntity<>("Instance created", HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Nije nadjen pacijent!", HttpStatus.FORBIDDEN);
    }





}
