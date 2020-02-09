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
public class KlinikaBazicnoDTO {
    private String naziv;
    private String adresa;
    private String opis;
    private int[] selLekari;
    private int[] selSestre;
    private int[] selSale;
    private int[] selAdmini;


}
