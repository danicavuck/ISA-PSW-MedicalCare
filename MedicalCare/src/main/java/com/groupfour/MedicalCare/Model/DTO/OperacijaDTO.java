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
public class OperacijaDTO {
    private int id;
    private LocalDateTime datumVreme;
    private int trajanjeOperacije;
    private int lekarId;
    private int pacijentId;
    private String imeLekara;
    private String prezimeLekara;
    private int salaId;
    private String pocetakTermina;
    private String krajTermina;
}
