package com.groupfour.MedicalCare;

import com.groupfour.MedicalCare.Model.Administrator.AdminKlinickogCentra;
import com.groupfour.MedicalCare.Model.Administrator.AdminKlinike;
import com.groupfour.MedicalCare.Model.DTO.UserRole;
import com.groupfour.MedicalCare.Model.HibernateUtil;
import com.groupfour.MedicalCare.Model.Klinika.Klinika;
import com.groupfour.MedicalCare.Model.Klinika.Sala;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Model.Osoblje.MedicinskaSestra;
import com.groupfour.MedicalCare.Utill.PasswordCheck;
import org.hibernate.Session;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class MedicalCareApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicalCareApplication.class, args);

        //testiranjeBaze();
        //citanjePodataka();
    }

    public static void testiranjeBaze() {
        Sala sala1 =
                Sala.builder().brojSale(101).zauzeta(false).pocetakTermina(LocalDateTime.of(2019, 12,20,12,0)).krajTermina(LocalDateTime.of(2019,12,20,12,30)).build();
        Sala sala2 =
                Sala.builder().brojSale(102).zauzeta(false).pocetakTermina(LocalDateTime.of(2019, 12,21,12,0)).krajTermina(LocalDateTime.of(2019,12,21,12,30)).build();
        Sala sala3 =
                Sala.builder().brojSale(103).zauzeta(false).pocetakTermina(LocalDateTime.of(2019, 12,22,12,0)).krajTermina(LocalDateTime.of(2019,12,22,13,30)).build();

        Sala sala44 =
                Sala.builder().brojSale(201).zauzeta(true).pocetakTermina(LocalDateTime.of(2019, 12,23,15,0)).krajTermina(LocalDateTime.of(2019,12,23,16,30)).build();


        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        if (session.isOpen()) {
            session.beginTransaction();
            Klinika klinika1 = session.load(Klinika.class, 1);
            Klinika klinika2 = session.load(Klinika.class, 2);

            klinika1.dodajSalu(sala1);
            klinika1.dodajSalu(sala2);
            klinika1.dodajSalu(sala3);
            klinika2.dodajSalu(sala44);

            session.update(klinika1);
            session.update(klinika2);



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

