package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.MedicalCareApplication;
import com.groupfour.MedicalCare.Model.Administrator.AdminKlinike;
import com.groupfour.MedicalCare.Model.DTO.PregledDTO;
import com.groupfour.MedicalCare.Model.Klinika.Klinika;
import com.groupfour.MedicalCare.Model.Klinika.Sala;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;
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
class PregledServiceTest {

    @Autowired
    LekarRepository lekarRepository;
    @Autowired
    SalaRepository salaRepository;
    @Autowired
    TipPregledaRepository tipPregledaRepository;
    @Autowired
    KlinikaRepository klinikaRepository;
    @Autowired
    PacijentRepository pacijentRepository;
    @MockBean
    CustomEmailSender customEmailSenderMock;

    @Autowired
    PregledService pregledService;

    @Test
    @Transactional
    void dodaljivanjeSalePregledu() {
        TipPregleda tipPregleda = TipPregleda.builder().id(1).tipPregleda("Pregled").build();
        Sala sala = Sala.builder().id(1).nazivSale("Sala 1").build();
        Lekar lekar =
                Lekar.builder().id(1).ime("Petar").prezime("Kovacevic").pocetakRadnogVremena(9).krajRadnogVremena(17).build();
        tipPregledaRepository.save(tipPregleda);
        salaRepository.save(sala);
        lekarRepository.save(lekar);
        PregledDTO pregledDTO =
                PregledDTO.builder().salaId(1).lekar(lekar.getId()).popust(0).trajanjePregleda(45).cena(1000).datumVreme(LocalDateTime.of(LocalDate.now(), LocalTime.of(12,0,0))).tipPregleda("Pregled").build();

        ResponseEntity<?> responseEntity = PregledService.kreirajNoviPregled(pregledDTO);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    @Transactional
    void pogresnoVremePregleda() {
        TipPregleda tipPregleda = TipPregleda.builder().id(1).tipPregleda("Pregled").build();
        Sala sala = Sala.builder().id(1).nazivSale("Sala 1").build();
        Lekar lekar =
                Lekar.builder().id(1).ime("Petar").prezime("Kovacevic").pocetakRadnogVremena(9).krajRadnogVremena(10).build();
        tipPregledaRepository.save(tipPregleda);
        salaRepository.save(sala);
        lekarRepository.save(lekar);
        PregledDTO pregledDTO =
                PregledDTO.builder().salaId(1).lekar(lekar.getId()).popust(0).trajanjePregleda(45).cena(1000).datumVreme(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT)).tipPregleda("Pregled").build();

        ResponseEntity<?> responseEntity = PregledService.kreirajNoviPregled(pregledDTO);
        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    @Test
    @Transactional
    void zapocinjanjeNovogPregleda() {
        TipPregleda tipPregleda = TipPregleda.builder().id(1).tipPregleda("Pregled").build();
        Sala sala = Sala.builder().id(1).nazivSale("Sala 1").build();
        Klinika klinika = Klinika.builder().id(1).naziv("Klinika 1").build();
        AdminKlinike adminKlinike =
                AdminKlinike.builder().id(1).klinika(klinika).ime("Petar").prezime("Kovacevic").email("petar" +
                        ".kovacevic0088@gmail.com").build();
        Lekar lekar =
                Lekar.builder().id(1).ime("Petar").prezime("Kovacevic").pocetakRadnogVremena(9).krajRadnogVremena(10).klinika(klinika).build();
        klinika.dodajAdmina(adminKlinike);
        klinikaRepository.save(klinika);
        tipPregledaRepository.save(tipPregleda);
        salaRepository.save(sala);
        lekarRepository.save(lekar);

        Pacijent pacijent = Pacijent.builder().ime("Marko").prezime("Markovic").id(1).email("markomarkovicpacijent" +
                "@gmail" +
                ".com").build();
        pacijentRepository.save(pacijent);

        HttpSession session = new MockHttpSession();
        session.setAttribute("id", 1);
        session.setAttribute("role", "lekar");

        PregledDTO pregledDTO =
                PregledDTO.builder().salaId(1).lekar(lekar.getId()).popust(0).trajanjePregleda(45).cena(1000).datumVreme(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT)).tipPregleda("Pregled").pacijent(1).build();

        ResponseEntity<?> responseEntity = PregledService.zapocniNoviPregled(pregledDTO, session);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    @Transactional
    void neregularnoZapocinjanjeNovogPregleda() {
        TipPregleda tipPregleda = TipPregleda.builder().id(1).tipPregleda("Pregled").build();
        Sala sala = Sala.builder().id(1).nazivSale("Sala 1").build();
        Klinika klinika = Klinika.builder().id(1).naziv("Klinika 1").build();
        AdminKlinike adminKlinike =
                AdminKlinike.builder().id(1).klinika(klinika).ime("Petar").prezime("Kovacevic").email("petar" +
                        ".kovacevic0088@gmail.com").build();
        Lekar lekar =
                Lekar.builder().id(1).ime("Petar").prezime("Kovacevic").pocetakRadnogVremena(9).krajRadnogVremena(10).klinika(klinika).build();
        klinika.dodajAdmina(adminKlinike);
        klinikaRepository.save(klinika);
        tipPregledaRepository.save(tipPregleda);
        salaRepository.save(sala);
        lekarRepository.save(lekar);

        Pacijent pacijent = Pacijent.builder().ime("Marko").prezime("Markovic").id(1).email("markomarkovicpacijent" +
                "@gmail" +
                ".com").build();
        pacijentRepository.save(pacijent);

        HttpSession session = new MockHttpSession();
        session.setAttribute("id", 501);
        session.setAttribute("role", "lekar");

        PregledDTO pregledDTO =
                PregledDTO.builder().salaId(1).lekar(lekar.getId()).popust(0).trajanjePregleda(45).cena(1000).datumVreme(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT)).tipPregleda("Pregled").pacijent(1).build();

        ResponseEntity<?> responseEntity = PregledService.zapocniNoviPregled(pregledDTO, session);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}