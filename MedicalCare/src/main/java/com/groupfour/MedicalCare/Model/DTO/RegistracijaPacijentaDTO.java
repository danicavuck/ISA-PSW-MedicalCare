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
public class RegistracijaPacijentaDTO {
    private int id;
    private String ime;
    private String prezime;
    private String email;
    private boolean aktivan;
    private boolean odobren = false;
    private String poruka;

}
