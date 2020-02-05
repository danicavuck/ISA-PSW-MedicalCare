package com.groupfour.MedicalCare.Model.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ReceptDTO {
    private int id;
    private int idLeka;
    private String kodLeka;
    private String nazivLeka;
    private int idMedSestre;
}
