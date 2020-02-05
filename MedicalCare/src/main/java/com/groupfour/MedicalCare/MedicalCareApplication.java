package com.groupfour.MedicalCare;

import com.groupfour.MedicalCare.Model.Administrator.AdminKlinickogCentra;
import com.groupfour.MedicalCare.Model.DTO.UserRole;
import com.groupfour.MedicalCare.Model.Klinika.Klinika;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Model.Osoblje.MedicinskaSestra;
import com.groupfour.MedicalCare.Repository.KlinikaRepository;
import com.groupfour.MedicalCare.Repository.MedicinskaSestraRepository;
import com.groupfour.MedicalCare.Utill.HibernateUtil;
import com.groupfour.MedicalCare.Utill.PasswordCheck;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class MedicalCareApplication {

    private static MedicinskaSestraRepository medicinskaSestraRepository;
    private static KlinikaRepository klinikaRepository;

    @Autowired
    public MedicalCareApplication(MedicinskaSestraRepository mRepo,KlinikaRepository klinikaRepo){
        this.medicinskaSestraRepository = mRepo;
        this.klinikaRepository = klinikaRepo;
    }

    public static void main(String[] args) {
        AdminKlinickogCentra adminKlinickogCentra = AdminKlinickogCentra.builder().ime("admin").email("admin@gmail.com").lozinka(PasswordCheck.hash("adminKC")).aktivan(true).prezime("admin").prviPutLogovan(false).build();
        UserRole userRole = UserRole.builder().user_email("admin@gmail.com").role("adminkc").build();

        MedicinskaSestra medicinskaSestra = MedicinskaSestra.builder().email("medicinskasestra@gmail.com").lozinka(PasswordCheck.hash("medicinskasestra")).ime("Sestra").prezime("Sestra").build();
        UserRole roleSestra = UserRole.builder().user_email("medicinskasestra@gmail.com").role("med_sestra").prvoLogovanje(false).build();

        Lekar lekar = Lekar.builder().ime("Lekar").email("lekar@gmail.com").lozinka(PasswordCheck.hash("lekarlekar")).aktivan(true).prvoLogovanje(false).build();
        UserRole roleLekar = UserRole.builder().user_email("lekar@gmail.com").role("lekar").prvoLogovanje(false).build();

        Set<Lekar> lista_lekara = new HashSet<>();
        lista_lekara.add(lekar);
        Set<MedicinskaSestra> lista_sestara = new HashSet<>();
        lista_sestara.add(medicinskaSestra);
        Klinika klinika = Klinika.builder().listaLekara(lista_lekara).listaSestara(lista_sestara).adresa("Fruskogorska").naziv("Velika klinika").opis("Velika").build();
        medicinskaSestra.setKlinika(klinika);
       //napuniBazu(klinika);


       // napuniBazu(medicinskaSestra);


        SpringApplication.run(MedicalCareApplication.class, args);


    }

    public static void napuniBazu(Object objekat){

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        if (session.isOpen()) {
            session.beginTransaction();
           // session.save(rola);
            session.save(objekat);
            session.getTransaction().commit();
            session.close();

        }

    }


}

