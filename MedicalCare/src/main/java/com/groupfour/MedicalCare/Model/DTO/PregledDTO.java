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
    private int id;
    private LocalDateTime datumVreme;
    private int trajanjePregleda;
    private int cena;
    private int popust;
    private String sala;
    private int salaId;
    private String tipPregleda;
    private int lekar;
    private String lekarImeIPrezime;
    private String pocetakTermina;
    private String krajTermina;
    private int pacijent;
    private String imeLekara;
    private String prezimeLekara;
}
