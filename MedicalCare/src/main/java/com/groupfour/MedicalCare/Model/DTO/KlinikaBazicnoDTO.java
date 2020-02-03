package com.groupfour.MedicalCare.Model.DTO;

import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Repository.LekarRepository;
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
    private int[] selLekari;
    private int[] selSestre;
    private int[] selSale;
    private int[] selAdmini;


}
