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
@Table(name = DbTableConstants.SIFARNIK_DIJAGNOZA)
public class SifarnikDijagnoza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DbColumnConstants.SIFARNIK_DIJAGNOZA_ID)
    private int id;
    @Column(name = DbColumnConstants.SIFARNIK_DIJAGNOZA_KOD_BOLESTI)
    private String kodBolesti;
    @Column(name = DbColumnConstants.SIFARNIK_DIJAGNOZA_NAZIV_BOLESTI)
    private String nazivBolesti;
    @Column(name = DbColumnConstants.SIFARNIK_DIJAGNOZA_AKTIVAN)
    private boolean aktivan = true;

}
