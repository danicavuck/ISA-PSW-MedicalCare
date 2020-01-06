package com.groupfour.MedicalCare.Model.DTO;

import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PregledDTO {
    private LocalDateTime datumVreme;
    private int trajanjePregleda;
    private int cena;
    private int popust;
    private int sala;
    private String tipPregleda;
    private int lekar;
    private String lekarImeIPrezime;
    private String pocetakTermina;
    private String krajTermina;
    private int pacijent;
}
