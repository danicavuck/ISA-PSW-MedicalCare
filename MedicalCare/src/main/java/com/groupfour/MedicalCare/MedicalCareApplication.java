package com.groupfour.MedicalCare;

import com.groupfour.MedicalCare.Model.Administrator.AdminKlinickogCentra;
import com.groupfour.MedicalCare.Model.Administrator.AdminKlinike;
import com.groupfour.MedicalCare.Model.DTO.UserRole;
import com.groupfour.MedicalCare.Model.HibernateUtil;
import com.groupfour.MedicalCare.Model.Klinika.Klinika;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Model.Osoblje.MedicinskaSestra;
import com.groupfour.MedicalCare.Utill.PasswordCheck;
import org.hibernate.Session;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MedicalCareApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicalCareApplication.class, args);

        //testiranjeBaze();
        //citanjePodataka();
    }

    public static void testiranjeBaze() {
        Klinika klinika1 = Klinika.builder().naziv("Laza").adresa("Adresa BB").opis("Lep opis").build();
        Klinika klinika2 = Klinika.builder().naziv("Infektivna").adresa("Adresa 121").opis("Nista posebno").build();
        Klinika klinika3 = Klinika.builder().naziv("Klinika Lepa").adresa("Adresa 101").opis("Opis na pretek").build();
        Klinika klinika4 = Klinika.builder().naziv("Klinika Ruzna").adresa("Adresa 510").opis("Bas ruzna").build();

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        if (session.isOpen()) {
            session.beginTransaction();

            session.save(klinika1);
            session.save(klinika2);
            session.save(klinika3);
            session.save(klinika4);

            session.getTransaction().commit();
            session.close();
        } else {
            System.out.println("Couldn't open connection with database");
        }

    }

    public static void citanjePodataka() {
        // Primer citanja podataka iz baze, modifikovati po potrebi
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        if (session.isOpen()) {
            session.beginTransaction();
            AdminKlinickogCentra adminKlinickogCentra = session.load(AdminKlinickogCentra.class, 1);


            session.getTransaction().commit();
            session.close();
        } else {
            System.out.println("Couldn't open connection with database");
        }
    }

}

