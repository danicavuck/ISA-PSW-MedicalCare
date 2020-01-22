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
public class AdminKlinikeDTO {
    int id;
    String ime;
    String prezime;
    String email;
    String lozinka;
    int id_klinike;
}
