package com.groupfour.MedicalCare;

import com.groupfour.MedicalCare.Model.Administrator.AdminKlinickogCentra;
import com.groupfour.MedicalCare.Model.HibernateUtil;
import com.groupfour.MedicalCare.Model.Klinika.Klinika;
import com.groupfour.MedicalCare.Model.Klinika.Sala;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Model.Osoblje.MedicinskaSestra;
import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;
import com.groupfour.MedicalCare.Model.Zahtevi.RegistracijaPacijenta;
import com.groupfour.MedicalCare.Utill.PasswordCheck;
import org.hibernate.Session;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.time.LocalDateTime;

@SpringBootApplication
public class MedicalCareApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicalCareApplication.class, args);

        //testiranjeBaze();
        //citanjePodataka();
    }

    public static void testiranjeBaze() {
        //Sala sala = Sala.builder().zauzeta(false).pocetakTermina(null).krajTermina(null).brojSale(101).build();
        AdminKlinickogCentra adminKlinickogCentra = AdminKlinickogCentra.builder().email("petar.kovacevic0088@gmail.com")
                .ime("Nikola").prezime("Nikolic").lozinka(PasswordCheck.hash("000000")).aktivan(true).prviPutLogovan(true).build();



        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        if (session.isOpen()) {
            session.beginTransaction();

            session.save(adminKlinickogCentra);
            //session.save(sala);

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

