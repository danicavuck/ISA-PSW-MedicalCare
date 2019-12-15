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
public class KlinikaDTO {
    private int id;
    private String naziv;
    private String adresa;
    private String opis;
}
