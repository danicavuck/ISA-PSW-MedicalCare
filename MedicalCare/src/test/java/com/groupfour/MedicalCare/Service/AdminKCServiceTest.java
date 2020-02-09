package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.MedicalCareApplication;
import com.groupfour.MedicalCare.Model.Administrator.AdminKlinickogCentra;
import com.groupfour.MedicalCare.Model.DTO.IzvestajDTO;
import com.groupfour.MedicalCare.Model.DTO.LekDTO;
import com.groupfour.MedicalCare.Model.DTO.PacijentDTO;
import com.groupfour.MedicalCare.Model.DTO.RegistracijaPacijentaDTO;
import com.groupfour.MedicalCare.Model.Dokumenti.IzvestajOPregledu;
import com.groupfour.MedicalCare.Model.Dokumenti.Karton;
import com.groupfour.MedicalCare.Model.Dokumenti.SifarnikLekova;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;
import com.groupfour.MedicalCare.Model.Zahtevi.RegistracijaPacijenta;
import com.groupfour.MedicalCare.Repository.*;
import com.groupfour.MedicalCare.Utill.CustomEmailSender;
import com.groupfour.MedicalCare.Utill.PasswordCheck;
import okhttp3.Response;
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

import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = MedicalCareApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
public class AdminKCServiceTest {
    @Autowired
    PacijentRepository pacijentRepository;
    @MockBean
    CustomEmailSender customEmailSenderMock;
    @Autowired
    AdminKCService adminKCService;
    @Autowired
    AdminKCRepository adminKCRepository;
    @Autowired
    LekarRepository lekarRepository;
    @Autowired
    IzvestajOPregleduRepository izvestajOPregleduRepository;
    @Autowired
    KartonRepository kartonRepository;
    @Autowired
    RegistracijaPacijentaRepository registracijaPacijentaRepository;
    @Autowired
    SifarnikLekovaRepository sifarnikLekovaRepository;

    @Test
    @Transactional
    void dodajPacijenta(){

        PacijentDTO pacijentDTO = PacijentDTO.builder().email("danicavuck@gmail.com").ime("Danica").prezime("Vuckovic").adresa("Strazilovska").
                                                brojOsiguranja("12").brojTelefona("03219165").grad("Novi sad").
                                                lozinka(PasswordCheck.hash("123456")).build();

        ResponseEntity<?> responseEntity = adminKCService.napraviNoviNalogPacijentu(pacijentDTO);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

    }

    @Test
    @Transactional
    void odbijZahtevZaRegistraciju(){
        AdminKlinickogCentra adminKlinickogCentra = AdminKlinickogCentra.builder().email("adminKC@gmail.com").ime("Milos").prezime("Milosevic")
                                                        .lozinka(PasswordCheck.hash("adminKC")).id(2).aktivan(true).build();

        RegistracijaPacijenta registracijaPacijenta = RegistracijaPacijenta.builder().id(1).email("danicavuck@gmail.com").ime("Danica")
                                .lozinka(PasswordCheck.hash("danica")).adresa("Strazilovska").brojOsiguranja("22").brojTelefona("61561")
                                .drzava("Srbija").grad("Novi sad").prezime("Vuckovic").odobren(false).aktivan(true).build();


        RegistracijaPacijentaDTO registracijaPacijentaDTO = RegistracijaPacijentaDTO.builder().id(registracijaPacijenta.getId())
                            .email(registracijaPacijenta.getEmail()).ime(registracijaPacijenta.getIme()).prezime(registracijaPacijenta.getPrezime())
                            .aktivan(true).odobren(false).poruka("Neadekvatan zahtev").build();


        adminKCRepository.save(adminKlinickogCentra);
        registracijaPacijentaRepository.save(registracijaPacijenta);

        HttpSession session = new MockHttpSession();
        session.setAttribute("role", "adminkc");
        session.setAttribute("id", 2);

        adminKCService.odbijZahtev(registracijaPacijentaDTO,session);
        RegistracijaPacijenta registracija = registracijaPacijentaRepository.getOne(registracijaPacijenta.getId());

        assertNotNull(registracija);
        assertFalse(registracija.isOdobren());

    }


    @Test
    @Transactional
    void dodajLek(){
        AdminKlinickogCentra adminKlinickogCentra = AdminKlinickogCentra.builder().email("admin@gmail.com").ime("Petar").prezime("Kovacevic").lozinka(PasswordCheck.hash("adminKC")).id(1).aktivan(true).build();
        adminKCRepository.save(adminKlinickogCentra);
        LekDTO lekDTO = LekDTO.builder().nazivLeka("Pantenol").kodLeka("123").build();
        HttpSession session = new MockHttpSession();
        session.setAttribute("role", "adminkc");
        session.setAttribute("id", 1);
        ResponseEntity<?> responseEntity = adminKCService.dodajLek(lekDTO,session);

        SifarnikLekova lek = sifarnikLekovaRepository.findByKodLeka(lekDTO.getKodLeka());
        assertNotNull(lek);
        assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
    }

    @Test
    @Transactional
    void dodajLekUnauthorized(){

        LekDTO lekDTO = LekDTO.builder().nazivLeka("Pantenol").kodLeka("123").build();
        HttpSession session = new MockHttpSession();
        session.setAttribute("role", "adminkc");
        session.setAttribute("id", 3);
        ResponseEntity<?> responseEntity = adminKCService.dodajLek(lekDTO,session);
        assertEquals(HttpStatus.UNAUTHORIZED,responseEntity.getStatusCode());
    }

    @Test
    @Transactional
    void dodajLekAlreadyinDb(){
        AdminKlinickogCentra adminKlinickogCentra = AdminKlinickogCentra.builder().email("admin@gmail.com").ime("Petar").prezime("Kovacevic").lozinka(PasswordCheck.hash("adminKC")).id(1).aktivan(true).build();
        SifarnikLekova sifarnikLekova = SifarnikLekova.builder().id(1).nazivLeka("Pantelnol").kodLeka("123").aktivan(true).build();

        sifarnikLekovaRepository.save(sifarnikLekova);
        adminKCRepository.save(adminKlinickogCentra);

        LekDTO lekDTO = LekDTO.builder().nazivLeka("Pantenol").kodLeka("123").build();

        HttpSession session = new MockHttpSession();
        session.setAttribute("role", "adminkc");
        session.setAttribute("id", 1);
        ResponseEntity<?> responseEntity = adminKCService.dodajLek(lekDTO,session);
        assertEquals(HttpStatus.FORBIDDEN,responseEntity.getStatusCode());
    }


    @Test
    @Transactional
    void dobaviIzvestajeZaPazijenta(){
        int idPacijent = 1;

        Pacijent pacijent = Pacijent.builder().ime("Marko").prezime("Markovic").email("marko@gmail.com").id(idPacijent).lozinka(PasswordCheck.hash("123456")).grad("Stara Pazova").build();
        Lekar lekar = Lekar.builder().ime("Milos").prezime("Petrovic").id(1).email("milospetrovic@gmail.com").lozinka(PasswordCheck.hash("123456")).aktivan(true).build();
        lekarRepository.save(lekar);
        Lekar l = lekarRepository.findLekarById(lekar.getId());

        IzvestajOPregledu izvestajOPregledu = IzvestajOPregledu.builder().lekar(l).informacijeOPregledu("Temperatura 12").pacijentId(idPacijent).aktivan(true).build();
        Set<IzvestajOPregledu> izvestaji = new HashSet<IzvestajOPregledu>();
        izvestaji.add(izvestajOPregledu);

        Karton karton = Karton.builder().pacijent(pacijent).id(1).izvestajiOPregledima(izvestaji).build();
        pacijent.dodajKarton(karton);

        pacijentRepository.save(pacijent);
        izvestajOPregleduRepository.save(izvestajOPregledu);
        kartonRepository.save(karton);

        HttpSession session = new MockHttpSession();
        session.setAttribute("role","lekar");
        session.setAttribute("id",1);
        ResponseEntity<?> responseEntity = IzvestajOPregleduService.dobaviSveIzvestajeZaPacijenta(session,idPacijent);

        IzvestajDTO izvestajDTO = IzvestajDTO.builder().emailPacijenta(pacijent.getEmail()).idPacijent(idPacijent).imePacijenta(pacijent.getIme())
                .prezimePacijenta(pacijent.getPrezime()).id(izvestajOPregledu.getId()).build();

        List<IzvestajDTO> izvestajDTOS = new ArrayList<IzvestajDTO>();
        izvestajDTOS.add(izvestajDTO);

        //assertEquals(responseEntity.getBody(),izvestajDTOS);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());

    }



}
