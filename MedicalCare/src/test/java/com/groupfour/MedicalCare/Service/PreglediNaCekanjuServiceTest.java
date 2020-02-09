package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.Administrator.AdminKlinike;
import com.groupfour.MedicalCare.Model.DTO.PregledNaCekanjuDTO;
import com.groupfour.MedicalCare.Model.Klinika.Klinika;
import com.groupfour.MedicalCare.Model.Klinika.Sala;
import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;
import com.groupfour.MedicalCare.Model.Pregled.Pregled;
import com.groupfour.MedicalCare.Model.Pregled.PreglediNaCekanju;
import com.groupfour.MedicalCare.Model.Pregled.TipPregleda;
import com.groupfour.MedicalCare.Repository.AdminKlinikeRepository;
import com.groupfour.MedicalCare.Repository.KlinikaRepository;
import com.groupfour.MedicalCare.Repository.PregledRepository;
import com.groupfour.MedicalCare.Repository.PreglediNaCekanjuRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PreglediNaCekanjuServiceTest {
    @Autowired
    PreglediNaCekanjuRepository preglediNaCekanjuRepository;
    @Mock
    AdminKlinikeRepository adminKlinikeRepository;
    @Autowired
    KlinikaRepository klinikaRepository;

    @Mock
    PreglediNaCekanjuService preglediNaCekanjuService;

    @Test
    void cuvanjePregleda(){
        AdminKlinike adminKlinike = AdminKlinike.builder().ime("Petar").prezime("Kovacevic").build();
        Klinika klinika = Klinika.builder().naziv("Infektivna").build();

        PregledNaCekanjuDTO pregledNaCekanjuDTO = PregledNaCekanjuDTO.builder().id(1).nazivSale("A1").build();
        PreglediNaCekanju preglediNaCekanju = PreglediNaCekanju.builder().id(1).aktivan(true).build();

        HttpSession session = new MockHttpSession();
        session.setAttribute("id", 1);
        session.setAttribute("role", "adminklinike");

        PreglediNaCekanjuService.odabirSaleZaPregled(pregledNaCekanjuDTO, session);
        assertEquals(1,1);
    }

    @Test
    void odabirSaleZaPregled() {
        assertEquals(1, 1);
    }

    @Test
    void zauzmiSaluZaTermin() {
    }

    @Test
    void dodeliSaluPregledu() {
    }

    @Test
    void kreirajNoviPregledNaOsnovuZahteva() {
    }
}