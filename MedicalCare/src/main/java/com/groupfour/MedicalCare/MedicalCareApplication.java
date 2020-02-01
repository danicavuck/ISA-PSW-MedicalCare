package com.groupfour.MedicalCare;

import com.groupfour.MedicalCare.Model.Administrator.AdminKlinickogCentra;
import com.groupfour.MedicalCare.Model.DTO.UserRole;
import com.groupfour.MedicalCare.Model.Osoblje.MedicinskaSestra;
import com.groupfour.MedicalCare.Utill.HibernateUtil;
import com.groupfour.MedicalCare.Utill.PasswordCheck;
import org.hibernate.Session;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MedicalCareApplication {

    public static void main(String[] args) {
        AdminKlinickogCentra adminKlinickogCentra = AdminKlinickogCentra.builder().ime("admin").email("admin@gmail.com").lozinka(PasswordCheck.hash("adminKC")).aktivan(true).prezime("admin").prviPutLogovan(true).build();
        UserRole userRole = UserRole.builder().user_email("admin@gmail.com").role("adminkc").build();

        MedicinskaSestra medicinskaSestra = MedicinskaSestra.builder().email("medicinskasestra@gmail.com").lozinka(PasswordCheck.hash("medicinskasestra")).ime("Sestra").prezime("Sestra").build();
        UserRole roleSestra = UserRole.builder().user_email("medicinskasestra@gmail.com").role("med_sestra").build();

//        napuniBazu(adminKlinickogCentra,userRole);
//        napuniBazu(medicinskaSestra,roleSestra);


        SpringApplication.run(MedicalCareApplication.class, args);


    }

    public static void napuniBazu(Object objekat, UserRole rola){

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        if (session.isOpen()) {
            session.beginTransaction();
            session.save(rola);
            session.save(objekat);
            session.getTransaction().commit();
            session.close();

        }

    }


}

