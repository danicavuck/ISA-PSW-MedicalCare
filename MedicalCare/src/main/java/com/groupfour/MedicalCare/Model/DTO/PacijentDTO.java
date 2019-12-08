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
public class PacijentDTO {
    private String email;
    private String lozinka;
    private String ime;
    private String prezime;
    private String adresaPrebivalista;
    private String grad;
    private String drzava;
    private String telefon;
    private String brojOsiguranja;
}
