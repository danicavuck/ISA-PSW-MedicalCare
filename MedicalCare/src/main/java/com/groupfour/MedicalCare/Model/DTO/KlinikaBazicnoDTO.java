package com.groupfour.MedicalCare.Model.DTO;

import lombok.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Set;

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
    private int[] lekari_id;
    private int[] sestre_id;
    private int[] sale_id;
    private int[] admini_id;
}
