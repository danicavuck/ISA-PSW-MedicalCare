package com.groupfour.MedicalCare.Model.Dokumenti;


import com.groupfour.MedicalCare.Common.db.DbColumnConstants;
import com.groupfour.MedicalCare.Common.db.DbTableConstants;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = DbTableConstants.SIFARNIK_LEKOVA)
public class SifarnikLekova {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DbColumnConstants.SIFARNIK_LEKOVA_ID)
    private int id;
    @Column(name = DbColumnConstants.SIFARNIK_LEKOVA_KOD_LEKA)
    private String kodLeka;
    @Column(name = DbColumnConstants.SIFARNIK_LEKOVA_NAZIV_LEKA)
    private String nazivLeka;
    @Column(name = DbColumnConstants.SIFARNIK_LEKOVA_AKTIVAN)
    private boolean aktivan = true;
}
