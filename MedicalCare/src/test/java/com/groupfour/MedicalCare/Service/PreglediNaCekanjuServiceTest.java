package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.MedicalCareApplication;
import com.groupfour.MedicalCare.Model.DTO.PregledNaCekanjuDTO;
import com.groupfour.MedicalCare.Model.Klinika.Sala;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;
import com.groupfour.MedicalCare.Model.Pregled.PreglediNaCekanju;
import com.groupfour.MedicalCare.Model.Pregled.TipPregleda;
import com.groupfour.MedicalCare.Repository.*;
import com.groupfour.MedicalCare.Utill.CustomEmailSender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = MedicalCareApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
class PreglediNaCekanjuServiceTest {
    @Autowired
    PreglediNaCekanjuRepository preglediNaCekanjuRepository;
    @Autowired
    SalaRepository salaRepository;
    @Autowired
    TipPregledaRepository tipPregledaRepository;
    @Autowired
    PacijentRepository pacijentRepository;
    @Autowired
    LekarRepository lekarRepository;
    @MockBean
    CustomEmailSender customEmailSenderMock;

    @Test
    @Transactional
    void odabirSaleZaPregled() {
        Lekar lekar = Lekar.builder().id(1).ime("Milos").prezime("Milosevic").email("petar.kovacevic0088@gmail.com").build();
        TipPregleda tipPregleda = TipPregleda.builder().id(1).tipPregleda("Tip 1").build();
        Pacijent pacijent = Pacijent.builder().id(1).ime("Petar").prezime("Kovacevic").build();
        Sala sala = Sala.builder().id(1).nazivSale("Sala 1").build();
        PreglediNaCekanju preglediNaCekanju =
                PreglediNaCekanju.builder().id(1).aktivan(true).terminPregleda(LocalDateTime.of(LocalDate.now(),
                        LocalTime.of(10,0,0))).trajanjePregleda(30).cena(1000).popust(0).sala(sala).lekar(lekar).build();
        PregledNaCekanjuDTO pregledNaCekanjuDTO = PregledNaCekanjuDTO.builder().id(1).nazivSale("Sala 1").build();

        lekarRepository.save(lekar);
        tipPregledaRepository.save(tipPregleda);
        pacijentRepository.save(pacijent);
        salaRepository.save(sala);
        preglediNaCekanjuRepository.save(preglediNaCekanju);

        HttpSession session = new MockHttpSession();
        session.setAttribute("role", "admin_klinike");
        session.setAttribute("id", 1);
        ResponseEntity<?> responseEntity = PreglediNaCekanjuService.odabirSaleZaPregled(pregledNaCekanjuDTO, session);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    @Transactional
    void negativanOdgovor() {
        Lekar lekar = Lekar.builder().id(1).ime("Milos").prezime("Milosevic").email("petar.kovacevic0088@gmail.com").build();
        TipPregleda tipPregleda = TipPregleda.builder().id(1).tipPregleda("Tip 1").build();
        Pacijent pacijent = Pacijent.builder().id(1).ime("Petar").prezime("Kovacevic").build();
        Sala sala = Sala.builder().id(1).nazivSale("Sala 1").build();
        PreglediNaCekanju preglediNaCekanju =
                PreglediNaCekanju.builder().id(1).aktivan(true).terminPregleda(LocalDateTime.of(LocalDate.now(),
                        LocalTime.of(10,0,0))).trajanjePregleda(30).cena(1000).popust(0).sala(sala).lekar(lekar).build();
        PregledNaCekanjuDTO pregledNaCekanjuDTO = PregledNaCekanjuDTO.builder().id(171).nazivSale("Sala 1").build();

        lekarRepository.save(lekar);
        tipPregledaRepository.save(tipPregleda);
        pacijentRepository.save(pacijent);
        salaRepository.save(sala);
        preglediNaCekanjuRepository.save(preglediNaCekanju);

        HttpSession session = new MockHttpSession();
        session.setAttribute("role", "admin_klinike");
        session.setAttribute("id", 1);
        ResponseEntity<?> responseEntity = PreglediNaCekanjuService.odabirSaleZaPregled(pregledNaCekanjuDTO, session);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
}