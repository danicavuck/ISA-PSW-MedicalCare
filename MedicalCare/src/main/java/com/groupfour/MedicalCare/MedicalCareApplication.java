package com.groupfour.MedicalCare;

import com.groupfour.MedicalCare.Model.Administrator.AdminKlinickogCentra;
import com.groupfour.MedicalCare.Model.Administrator.AdminKlinike;
import com.groupfour.MedicalCare.Model.DTO.UserRole;
import com.groupfour.MedicalCare.Utill.HibernateUtil;

import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Model.Osoblje.MedicinskaSestra;
import com.groupfour.MedicalCare.Utill.PasswordCheck;

import com.groupfour.MedicalCare.Model.Klinika.Klinika;

import org.hibernate.Session;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MedicalCareApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicalCareApplication.class, args);


        // testiranjeBaze();
       // citanjePodataka();
    }

    public static void testiranjeBaze() {

        Lekar lekar = Lekar.builder().email("lekar@gmail.com").ime("Milos").prezime("Petrovic").lozinka(PasswordCheck.hash("lekarlekar")).build();
        UserRole lekarRole = UserRole.builder().user_email("lekar@gmail.com").role("lekar").build();

        MedicinskaSestra medicinskaSestra = MedicinskaSestra.builder().email("sestra@gmail.com").ime("Marija").prezime("Kovacevic").lozinka(PasswordCheck.hash("sestrasestra")).build();
        UserRole sestraRole = UserRole.builder().user_email("sestra@gmail.com").role("med_sestra").build();

        AdminKlinike adminKlinike = AdminKlinike.builder().ime("Zoran").prezime("Premovic").aktivan(true).email("adminKlinike@gmail.com").lozinka(PasswordCheck.hash("adminklinike")).prviPutLogovan(true).build();
        UserRole adminRole = UserRole.builder().user_email("adminKlinike@gmail.com").role("admin_klinike").build();

        Klinika klinika = Klinika.builder().naziv("Klinicki centar").adresa("Hajduk Veljkova 1").opis("Pruza visok " +
                "kvalitet usluga velikom broju pacijenata").build();


        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        if (session.isOpen()) {
            session.beginTransaction();


            session.save(lekarRole);
            session.save(lekar);
            session.save(sestraRole);
            session.save(medicinskaSestra);
            session.save(adminRole);
            session.save(adminKlinike);


            session.saveOrUpdate(klinika);

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

