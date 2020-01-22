package com.groupfour.MedicalCare.Model.Pregled;

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
@Table(name = DbTableConstants.TIP_PREGLEDA)
public class TipPregleda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DbColumnConstants.TIP_PREGLEDA_ID)
    private int id;
    @Column(name = DbColumnConstants.TIP_PREGLEDA_PREDEFINISAN)
    private boolean predefinisan;
    @Column(name = DbColumnConstants.TIP_PREGLEDA_AKTIVAN)
    private boolean aktivan;
    @Column(name = DbColumnConstants.TIP_PREGLEDA_TEKST)
    private String tipPregleda;
    @Column(name = DbColumnConstants.TIP_PREGLEDA_KLINIKA_ID)
    private int klinikaId;

}
