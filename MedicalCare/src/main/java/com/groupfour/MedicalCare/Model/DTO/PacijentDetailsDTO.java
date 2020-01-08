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
public class PacijentDetailsDTO {
    private int id;
    private String email;
    private String ime;
    private String prezime;
    private String adresa;
    private String grad;
    private String drzava;
    private String brojTelefona;
    private String brojOsiguranja;
}

