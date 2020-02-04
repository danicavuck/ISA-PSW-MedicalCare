package com.groupfour.MedicalCare.Model.DTO;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OdsustvaZaAdminaDTO {
    private int idOdsustva;
    private String pocetakOdsustva;
    private String krajOdsustva;
    private String lekar;
    private String opis;
}
