package com.groupfour.MedicalCare.Model.DTO;

import com.groupfour.MedicalCare.Model.Klinika.Sala;
import com.groupfour.MedicalCare.Model.Pregled.TipPregleda;
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
    private LocalDateTime terminPregleda;
    private int trajanjePregleda;
    private int cena;
    private int popust;
    private SalaPretragaDTO sala;
    private TipPregledaDTO tipPregleda;
    private LekarDTO lekarDTO;
}
