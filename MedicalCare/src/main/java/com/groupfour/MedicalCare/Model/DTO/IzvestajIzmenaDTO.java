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
public class IzvestajIzmenaDTO {
    private int id;
    private String informacijeOPregledu;
    private int idDijagnoza;
    private int[] idLek;
}
