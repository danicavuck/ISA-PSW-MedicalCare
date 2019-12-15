package com.groupfour.MedicalCare.Model.DTO;

import com.groupfour.MedicalCare.Model.Administrator.AdminKlinike;
import com.groupfour.MedicalCare.Model.Klinika.Sala;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Model.Osoblje.MedicinskaSestra;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class KlinikaDTO {
    private int id;
    private String naziv;
    private String adresa;
    private String opis;
    private Set<Lekar> lekari;
    private Set<MedicinskaSestra> sestre;
    private Set<Sala> sale;
    private Set<AdminKlinike> admini;
}
