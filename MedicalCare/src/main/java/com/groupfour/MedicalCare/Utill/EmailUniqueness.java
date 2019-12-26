package com.groupfour.MedicalCare.Utill;


import com.groupfour.MedicalCare.Model.Administrator.AdminKlinickogCentra;
import com.groupfour.MedicalCare.Model.Administrator.AdminKlinike;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Model.Osoblje.MedicinskaSestra;
import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;
import com.groupfour.MedicalCare.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailUniqueness {
    private static AdminKCRepository adminKCRepository;
    private static AdminKlinikeRepository adminKlinikeRepository;
    private static LekarRepository lekarRepository;
    private static MedicinskaSestraRepository medicinskaSestraRepository;
    private static PacijentRepository pacijentRepository;

    @Autowired
    public EmailUniqueness(AdminKCRepository adminKCRepo, AdminKlinikeRepository adminKlinikeRepo,
                           LekarRepository lRepository, MedicinskaSestraRepository mSRepo, PacijentRepository pacRepo){
        adminKCRepository = adminKCRepo;
        adminKlinikeRepository = adminKlinikeRepo;
        lekarRepository = lRepository;
        medicinskaSestraRepository = mSRepo;
        pacijentRepository = pacRepo;
    }

    /*
    * Treba vrsiti proveru i za admina klinickog centra
    * */
    public static boolean isEmailUniqe(String email){
        AdminKlinickogCentra adminKlinickogCentra = adminKCRepository.findAdminKlinickogCentraByEmail(email);
        AdminKlinike adminKlinike = adminKlinikeRepository.findAdminKlinikeByEmail(email);
        Lekar lekar = lekarRepository.findLekarByEmail(email);
        MedicinskaSestra medicinskaSestra = medicinskaSestraRepository.findMedicinskaSestraByEmail(email);
        Pacijent pacijent = pacijentRepository.findUserByEmail(email);

        return (adminKlinickogCentra == null && adminKlinike == null && lekar == null && medicinskaSestra == null && pacijent == null);
    }
}
