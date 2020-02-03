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

@Service
public class IzvestajOPregleduService {

    private static IzvestajOPregleduRepository izvestajOPregleduRepository;
    private static SifarnikLekovaRepository sifarnikLekovaRepository;
    private static SifarnikDijagnozaRepository sifarnikDijagnozaRepository;
    private static LekarRepository lekarRepository;
    private static PacijentRepository pacijentRepository;
    private static KartonRepository kartonRepository;
    private static Logger logger = LoggerFactory.getLogger(Lekar.class);

    @Autowired
    public IzvestajOPregleduService(SifarnikDijagnozaRepository sdRepo,SifarnikLekovaRepository slRepo,LekarRepository lRepo,PacijentRepository pRepo,KartonRepository kRepo,IzvestajOPregleduRepository izRepo){
        sifarnikDijagnozaRepository = sdRepo;
        sifarnikLekovaRepository = slRepo;
        lekarRepository = lRepo;
        pacijentRepository = pRepo;
        kartonRepository = kRepo;
        izvestajOPregleduRepository = izRepo;
    }

    public ResponseEntity<?> dodajIzvestajOPregledu(IzvestajOPregleduDTO izvestajOPregleduDTO, HttpSession session) {
        Lekar lekar = lekarRepository.findLekarById((int) session.getAttribute("id"));
        Pacijent pacijent = pacijentRepository.findPacijentById(izvestajOPregleduDTO.getIdPacijent());
        Karton karton = kartonRepository.findKartonByPacijent(pacijent);

        if (lekar == null) {
            logger.error("Nije pronadjen lekar");
            return new ResponseEntity<>("Nije nadjen lekar", HttpStatus.UNAUTHORIZED);
        }

        if(pacijent != null){
            SifarnikLekova lek = sifarnikLekovaRepository.findSifarnikLekovaById(izvestajOPregleduDTO.getIdLek());
            SifarnikDijagnoza dijagnoza = sifarnikDijagnozaRepository.findSifarnikDijagnozaById(izvestajOPregleduDTO.getIdDijagnoza());

            Recept.builder().idLeka(izvestajOPregleduDTO.getIdLek()).lekar(lekar).overeno(false).build();
            IzvestajOPregledu izvestaj = IzvestajOPregledu.builder().aktivan(true).informacijeOPregledu(izvestajOPregleduDTO.getInformacijeOPregledu()).sifarnikDijagnoza(dijagnoza).sifarnikLekova(lek).build();
            karton.dodajDijagnozu(dijagnoza);
            karton.dodajIzvestaj(izvestaj);
            izvestajOPregleduRepository.save(izvestaj);
            return new ResponseEntity<>("Instance created", HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Nije nadjen pacijent!", HttpStatus.FORBIDDEN);
    }



}
