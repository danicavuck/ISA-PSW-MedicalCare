package com.groupfour.MedicalCare.Service;
import com.groupfour.MedicalCare.Model.Administrator.AdminKlinike;
import com.groupfour.MedicalCare.Model.DTO.OdsustvoDTO;
import com.groupfour.MedicalCare.Model.Osoblje.MedicinskaSestra;
import com.groupfour.MedicalCare.Model.Zahtevi.OdsustvoMedicinskeSestre;
import com.groupfour.MedicalCare.Repository.MedicinskaSestraRepository;
import com.groupfour.MedicalCare.Repository.OdsustvoMedicinskeSestreRepository;
import com.groupfour.MedicalCare.Repository.ReceptRepository;
import com.groupfour.MedicalCare.Utill.CustomEmailSender;

import com.groupfour.MedicalCare.Model.DTO.MedSestraIzmenaPodatakaDTO;
import com.groupfour.MedicalCare.Model.Osoblje.MedicinskaSestra;
import com.groupfour.MedicalCare.Repository.MedicinskaSestraRepository;
import com.groupfour.MedicalCare.Utill.PasswordCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashSet;
import java.util.Set;

@Service
public class MedicinskaSestraService {

    private static ReceptRepository receptRepository;

    private static MedicinskaSestraRepository medicinskaSestraRepository;

    private static OdsustvoMedicinskeSestreRepository odsustvoMedicinskeSestreRepository;

    private static CustomEmailSender customEmailSender;


    @Autowired
    public MedicinskaSestraService(ReceptRepository receptRepo, MedicinskaSestraRepository medRepo, OdsustvoMedicinskeSestreRepository oRepo, CustomEmailSender ceSender) {
        receptRepository = receptRepo;
        medicinskaSestraRepository = medRepo;
        odsustvoMedicinskeSestreRepository = oRepo;
        customEmailSender = ceSender;
    }


    public static ResponseEntity<?> dodajNoviZahtevZaOdsustvoMedicinskeSestre(OdsustvoDTO odsustvoDTO, HttpSession session) {
        MedicinskaSestra sestra = medicinskaSestraRepository.findMedicinskaSestraById(((int) session.getAttribute("id")));
        if (sestra != null) {
            OdsustvoMedicinskeSestre odsustvoMedicinskeSestre = OdsustvoMedicinskeSestre.builder().aktivno(true).pocetakOdsustva(odsustvoDTO.getDatumVreme()[0].atStartOfDay()).krajOdsustva(odsustvoDTO.getDatumVreme()[1].atStartOfDay()).odobren(false).medicinskaSestra(sestra).build();
            odsustvoMedicinskeSestreRepository.save(odsustvoMedicinskeSestre);
            slanjeMejlaAdminuOZahtevu(sestra, odsustvoDTO);

            return new ResponseEntity<>("Uspesno dodavanje zahteva za odsustvo", HttpStatus.CREATED);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public static void slanjeMejlaAdminuOZahtevu(MedicinskaSestra medicinskaSestra, OdsustvoDTO odsustvoDTO) {
        String pocetakOdsustva =
                odsustvoDTO.getDatumVreme()[0].format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
        String krajOdsustva =
                odsustvoDTO.getDatumVreme()[1].format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));

        String message =
                "<html><body><h3>Zahtev za odsustvo</h3><p>Postovani,</p><p>Lekar " + medicinskaSestra.getIme() + " " + medicinskaSestra.getPrezime() + " zeli da dobije odsustvo u periodu od " + pocetakOdsustva + " do " + krajOdsustva + "</p><p>Molimo Vas da razmotrite zahtev u dogledno vreme.</p><p>Srdacan pozdrav,</p><p>Medical Care</p></body></html>";

        Set<AdminKlinike> adminiKlinike = medicinskaSestra.getKlinika().getAdminiKlinike();
        String[] adrese = new String[adminiKlinike.size()];
        int i = 0;
        for (AdminKlinike admin : adminiKlinike) {
            adrese[i] = admin.getEmail();
            ++i;
        }

        customEmailSender.sendMail(adrese, "Zahtev za odsustvo", message);

    }


    public static ResponseEntity<?> azurirajPodatkeMedicinskeSestre(MedSestraIzmenaPodatakaDTO medSestraIzmenaPodatakaDTO, HttpSession session)
    {
        MedicinskaSestra medicinskaSestra =
                medicinskaSestraRepository.findMedicinskaSestraById((int) session.getAttribute("id"));
        if(medicinskaSestra != null)
        {
            if(!medSestraIzmenaPodatakaDTO.getEmail().equals(""))
                medicinskaSestra.setEmail(medSestraIzmenaPodatakaDTO.getEmail());
            if(!medSestraIzmenaPodatakaDTO.getIme().equals(""))
                medicinskaSestra.setIme(medSestraIzmenaPodatakaDTO.getIme());
            if(!medSestraIzmenaPodatakaDTO.getPrezime().equals(""))
                medicinskaSestra.setPrezime(medSestraIzmenaPodatakaDTO.getPrezime());
            if(trebaIzmenitiLozinku(medicinskaSestra, medSestraIzmenaPodatakaDTO))
                medicinskaSestra.setLozinka(PasswordCheck.hash(medSestraIzmenaPodatakaDTO.getNovaLozinka()));

            medicinskaSestraRepository.save(medicinskaSestra);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public static boolean trebaIzmenitiLozinku(MedicinskaSestra medicinskaSestra, MedSestraIzmenaPodatakaDTO dto){
        if(!dto.getNovaLozinka().equals("") && PasswordCheck.verifyHash(dto.getStaraLozinka(), medicinskaSestra.getLozinka()))
        {
            return true;
        }
        return false;
    }
}


