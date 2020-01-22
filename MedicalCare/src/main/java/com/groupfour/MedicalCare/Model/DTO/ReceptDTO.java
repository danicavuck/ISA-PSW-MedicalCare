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
    private String idLeka;
    private int idSestre;
}
