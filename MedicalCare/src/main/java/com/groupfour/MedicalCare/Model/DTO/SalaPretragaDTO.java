package com.groupfour.MedicalCare.Model.DTO;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SalaPretragaDTO {
    private String nazivSale;
    private Date datum;
}
