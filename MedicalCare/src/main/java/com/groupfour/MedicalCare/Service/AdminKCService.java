package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.Administrator.AdminKlinickogCentra;
import com.groupfour.MedicalCare.Model.Administrator.AdminKlinike;
import com.groupfour.MedicalCare.Model.DTO.*;
import com.groupfour.MedicalCare.Model.Dokumenti.Karton;
import com.groupfour.MedicalCare.Model.Dokumenti.SifarnikDijagnoza;
import com.groupfour.MedicalCare.Model.Dokumenti.SifarnikLekova;
import com.groupfour.MedicalCare.Model.Klinika.Klinika;
import com.groupfour.MedicalCare.Model.Klinika.Sala;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Model.Osoblje.MedicinskaSestra;
import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;
import com.groupfour.MedicalCare.Model.Zahtevi.RegistracijaPacijenta;
import com.groupfour.MedicalCare.Repository.*;
import com.groupfour.MedicalCare.Utill.CustomEmailSender;
import com.groupfour.MedicalCare.Utill.HibernateUtil;
import com.groupfour.MedicalCare.Utill.PasswordCheck;
import org.hibernate.Session;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OptimisticLockException;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AdminKCService {
    private static AdminKCRepository adminKCRepository;
    private RegistracijaPacijentaRepository registracijaPacijentaRepository;
    private KlinikaRepository klinikaRepository;
    private static Logger logger = LoggerFactory.getLogger(AdminKlinickogCentra.class);
    private SifarnikDijagnozaRepository sifarnikDijagnozaRepository;
    private SifarnikLekovaRepository sifarnikLekovaRepository;
    private static LekarRepository lekarRepository;
    private static MedicinskaSestraRepository medicinskaSestraRepository;
    private static AdminKlinikeRepository adminKlinikeRepository;
    private static SalaRepository salaRepository;
    private static KartonRepository kartonRepository;

    private static CustomEmailSender customEmailSender;

    @Autowired
    public AdminKCService(AdminKCRepository aKCRepository, RegistracijaPacijentaRepository regRepo,
                          KlinikaRepository kRepo , SifarnikDijagnozaRepository sfRepo,SifarnikLekovaRepository slRepo, LekarRepository lRepo, MedicinskaSestraRepository mRepo,AdminKlinikeRepository aRepo,SalaRepository sRepo, CustomEmailSender cmail,KartonRepository kartonRepo) {
        adminKCRepository = aKCRepository;
        registracijaPacijentaRepository = regRepo;
        klinikaRepository = kRepo;
        sifarnikDijagnozaRepository = sfRepo;
        sifarnikLekovaRepository = slRepo;
        lekarRepository = lRepo;
        medicinskaSestraRepository = mRepo;
        adminKlinikeRepository = aRepo;
        salaRepository = sRepo;
        kartonRepository = kartonRepo;
        customEmailSender = cmail;
    }

    public AdminKlinickogCentra getByEmail(String email, String lozinka) {
        AdminKlinickogCentra adminKC = adminKCRepository.findAdminKlinickogCentraByEmail(email);

        if (adminKC.getLozinka().equals(lozinka)) {
            return adminKC;
        }
        return null;
    }

    public AdminKlinickogCentra promenaLozinke(AdminKlinickogCentra adminKlinickogCentra) {
        adminKlinickogCentra.setPrviPutLogovan(false);
        return adminKCRepository.save(adminKlinickogCentra);
    }

    @Transactional(readOnly = false)
    public void prihvatiZahtev(RegistracijaPacijentaDTO registracijaPacijentaDTO, HttpSession session) {
        AdminKlinickogCentra adminKlinickogCentra = adminKCRepository.findAdminKlinickogCentraById((int)session.getAttribute("id"));
        if(adminKlinickogCentra == null){
            logger.error("Nije pronadjen admin klinickog centra");
            return;
        }
        try {
            RegistracijaPacijenta registracijaPacijenta = registracijaPacijentaRepository.getOne(registracijaPacijentaDTO.getId());
            registracijaPacijenta.setOdobren(true);
            registracijaPacijenta.setAktivan(false);
            posaljiMejlKorisniku(registracijaPacijentaDTO,1);
            PacijentDTO pacijentDTO = mapiranjePacijenta(registracijaPacijenta);
            napraviNoviNalogPacijentu(pacijentDTO);
            registracijaPacijentaRepository.save(registracijaPacijenta);

        } catch (OptimisticLockException e) {
            System.out.println("exception");
        }
    }

    @Transactional(readOnly = false)
    public void odbijZahtev(RegistracijaPacijentaDTO registracijaPacijentaDTO, HttpSession session) {
        AdminKlinickogCentra adminKlinickogCentra = adminKCRepository.findAdminKlinickogCentraById((int)session.getAttribute("id"));

        if(adminKlinickogCentra == null){
            logger.error("Nije pronadjen admin klinickog centra");
            return;
        }
        try {
            RegistracijaPacijenta registracijaPacijenta = registracijaPacijentaRepository.getOne(registracijaPacijentaDTO.getId());
            registracijaPacijenta.setOdobren(false);
            registracijaPacijenta.setAktivan(false);
            registracijaPacijenta.setRazlogOdbijanja(registracijaPacijentaDTO.getPoruka());
            posaljiMejlKorisniku(registracijaPacijentaDTO,0);
            registracijaPacijentaRepository.save(registracijaPacijenta);
        } catch (OptimisticLockException e) {
            System.out.println("exception");
            // osvesi sajt
        }

    }

    public static void posaljiMejlKorisniku(RegistracijaPacijentaDTO registracijaPacijentaDTO,int i){

        String message1 = "<html><body><h3>Prihvacen zahtev za registraciju</h3><p>Postovani,</p><p>" + registracijaPacijentaDTO.getIme() + " " + " Vas zahtev za registraciju je prihvacen od strane Administratora klinickog centra.</p><p>Srdacan pozdrav,</p><p>Medical Care</p></body></html>";


        String message0 =
                "<html><body><h3>Razlog odbijanja zahteva za registraciju</h3><p>Postovani,</p><p>" + registracijaPacijentaDTO.getIme() + " " + " Vas zahtev za registraciju je odbijen od strane Administratora klinickog centra. </p><p>Razlog zbog kog je zahtev odbijen: " + registracijaPacijentaDTO.getPoruka() + "</p><p>Srdacan pozdrav,</p><p>Medical Care</p></body></html>";


        String[] adrese = new String[1];
        adrese[0] = registracijaPacijentaDTO.getEmail();
        if(i == 0)
            customEmailSender.sendMail(adrese, "Zahtev za registraciju na Medical Care", message0);
        else
            customEmailSender.sendMail(adrese, "Zahtev za registraciju na Medical Care", message1);

    }

    public ResponseEntity<?> dodajKliniku(KlinikaBazicnoDTO klinika, HttpSession session) {
        Klinika temp = klinikaRepository.findByNaziv(klinika.getNaziv());
        AdminKlinickogCentra adminKlinickogCentra = adminKCRepository.findAdminKlinickogCentraById((int)session.getAttribute("id"));

        if(adminKlinickogCentra == null){
            logger.error("Nije pronadjen admin klinickog centra");
            return new ResponseEntity<>("Nije nadjen admin klinickog centra",HttpStatus.UNAUTHORIZED);
        }

        if (temp == null) {
        Set<Lekar> lekari = new HashSet<Lekar>();
        Set<MedicinskaSestra> sestre = new HashSet<MedicinskaSestra>();
        Set<AdminKlinike> admini = new HashSet<AdminKlinike>();
        Set<Sala> sale = new HashSet<Sala>();
        int[] id_lekara = klinika.getSelLekari();
        int[] id_sestara = klinika.getSelSestre();
        int[] id_admina = klinika.getSelAdmini();
        int[] id_sala = klinika.getSelSale();

            for(int i = 0 ; i < klinika.getSelLekari().length ; i++){
                        lekari.add(lekarRepository.findLekarById(id_lekara[i]));
            }
            for(int i = 0 ; i < klinika.getSelSestre().length ; i++){
                sestre.add(medicinskaSestraRepository.findMedicinskaSestraById(id_sestara[i]));
            }
            for(int i = 0 ; i < klinika.getSelAdmini().length ; i++){
                admini.add(adminKlinikeRepository.findAdminKlinikeById(id_admina[i]));
            }
            for(int i = 0 ; i < klinika.getSelSale().length ; i++){
                sale.add(salaRepository.findById(id_sala[i]));
            }



            temp = Klinika.builder().naziv(klinika.getNaziv()).adresa(klinika.getAdresa()).opis(klinika.getOpis()).listaLekara(lekari).listaSestara(sestre).spisakSala(sale).adminiKlinike(admini).build();
            for(Lekar l : lekari){
                l.setKlinika(temp);
            }
            for(MedicinskaSestra m : sestre){
                m.setKlinika(temp);
            }
            for(AdminKlinike a : admini){
                a.setKlinika(temp);
            }
            for(Sala s : sale){
                s.setKlinika(temp);
            }



            klinikaRepository.save(temp);

            return new ResponseEntity<>("Instance created", HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Klinika sa tim nazivom vec postoji!", HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<?> dodajAdminaKlinike(AdminKlinikeBazicnoDTO admin, HttpSession session) {
        AdminKlinike temp = adminKlinikeRepository.findAdminKlinikeByEmail(admin.getEmail());
        AdminKlinickogCentra adminKlinickogCentra = adminKCRepository.findAdminKlinickogCentraById((int)session.getAttribute("id"));

        if(adminKlinickogCentra == null){
            logger.error("Nije pronadjen admin klinickog centra");
            return new ResponseEntity<>("Nije nadjen admin klinickog centra",HttpStatus.UNAUTHORIZED);
        }

        if (temp == null) {

            Klinika klinika = klinikaRepository.findById(admin.getId_klinika());
            temp = AdminKlinike.builder().prviPutLogovan(true).aktivan(true).ime(admin.getIme()).prezime(admin.getPrezime()).email(admin.getEmail()).lozinka(PasswordCheck.hash(admin.getLozinka())).klinika(klinika).build();
            klinika.dodajAdmina(temp);

            adminKlinikeRepository.save(temp);

            return new ResponseEntity<>("Instance created", HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Admin klinike sa tim nazivom vec postoji!", HttpStatus.FORBIDDEN);
    }
    public ResponseEntity<?> dodajDijagnozu(DijagnozaDTO dijagnozaDTO, HttpSession session) {
         SifarnikDijagnoza temp = sifarnikDijagnozaRepository.findByKodBolesti(dijagnozaDTO.getKodBolesti());
        AdminKlinickogCentra adminKlinickogCentra = adminKCRepository.findAdminKlinickogCentraById((int)session.getAttribute("id"));

      if(adminKlinickogCentra == null){
            logger.error("Nije pronadjen admin klinickog centra");
            return new ResponseEntity<>("Nije nadjen admin klinickog centra",HttpStatus.UNAUTHORIZED);
        }

        if (temp == null) {
            temp = SifarnikDijagnoza.builder().aktivan(true).kodBolesti(dijagnozaDTO.getKodBolesti()).nazivBolesti(dijagnozaDTO.getNazivBolesti()).build();

            sifarnikDijagnozaRepository.save(temp);

            return new ResponseEntity<>("Instance created", HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Dijagnoza tim kodom postoji!", HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<?> dodajLek(LekDTO lekDTO, HttpSession session) {
        SifarnikLekova temp = sifarnikLekovaRepository.findByKodLeka(lekDTO.getKodLeka());
        AdminKlinickogCentra adminKlinickogCentra = adminKCRepository.findAdminKlinickogCentraById((int)session.getAttribute("id"));


        if(adminKlinickogCentra == null){
            logger.error("Nije pronadjen admin klinickog centra");
            return new ResponseEntity<>("Nije nadjen admin klinickog centra",HttpStatus.UNAUTHORIZED);
        }

        if (temp == null) {
            temp = SifarnikLekova.builder().aktivan(true).nazivLeka(lekDTO.getNazivLeka()).kodLeka(lekDTO.getKodLeka()).build();

            sifarnikLekovaRepository.save(temp);

            return new ResponseEntity<>("Instance created", HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Lektim kodom postoji!", HttpStatus.FORBIDDEN);
    }




    public List<SifarnikDijagnoza> getDijagnoze(HttpSession session) {
        AdminKlinickogCentra adminKlinickogCentra = adminKCRepository.findAdminKlinickogCentraById((int)session.getAttribute("id"));
        Lekar lekar = lekarRepository.findLekarById((int)session.getAttribute("id"));

        if(adminKlinickogCentra == null && lekar == null){
            logger.error("Nije pronadjen admin klinickog centra");
            return null;
        }

        List<SifarnikDijagnoza> temp = new ArrayList<>();
        List<SifarnikDijagnoza> all = sifarnikDijagnozaRepository.findAll();

        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).isAktivan()) {
                temp.add(all.get(i));
            }
        }

        return temp;
    }
    public List<SifarnikLekova> getLekovi(HttpSession session) {
        AdminKlinickogCentra adminKlinickogCentra = adminKCRepository.findAdminKlinickogCentraById((int)session.getAttribute("id"));
        Lekar lekar = lekarRepository.findLekarById((int)session.getAttribute("id"));

        if(adminKlinickogCentra == null && lekar == null){
            logger.error("Nije pronadjen admin klinickog centra");
            return null;
        }
        List<SifarnikLekova> temp = new ArrayList<>();
        List<SifarnikLekova> all = sifarnikLekovaRepository.findAll();

        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).isAktivan()) {
                temp.add(all.get(i));
            }
        }

        return temp;
    }

    public static ResponseEntity<String> napraviNoviNalogPacijentu(PacijentDTO pacijentDTO) {
        Pacijent pacijent = napraviNovogPacijenta(pacijentDTO);
        UserRole userRole = UserRole.builder().user_email(pacijentDTO.getEmail()).role("pacijent").build();
        //kreiranje zdravstvenog kartona pacijenta
        Karton karton = Karton.builder().aktivan(true).pacijent(pacijent).build();

        pacijent.dodajKarton(karton);
        if (sacuvajUBazuPacijentaIRolu(pacijent, userRole)) {
            return new ResponseEntity<>("Instance created", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Internal server eror", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    public static Pacijent napraviNovogPacijenta(PacijentDTO pacijentDTO) {
        return Pacijent.builder().email(pacijentDTO.getEmail()).lozinka(pacijentDTO.getLozinka()).ime(pacijentDTO.getIme()).prezime(pacijentDTO.getPrezime()).adresa(pacijentDTO.getAdresa()).grad(pacijentDTO.getGrad()).drzava(pacijentDTO.getDrzava()).brojTelefona(pacijentDTO.getBrojTelefona()).brojOsiguranja(pacijentDTO.getBrojOsiguranja()).build();

    }


    public static boolean sacuvajUBazuPacijentaIRolu(Pacijent pacijent, UserRole userRole) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        if (session.isOpen()) {
            session.beginTransaction();
            session.save(userRole);
            session.save(pacijent);
            session.getTransaction().commit();
            session.close();
            return true;
        }

        return false;
    }

    public static PacijentDTO mapiranjePacijenta(RegistracijaPacijenta registracijaPacijenta){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(registracijaPacijenta,PacijentDTO.class);
    }

}
