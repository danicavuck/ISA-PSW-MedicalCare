package com.groupfour.MedicalCare.Model.Klinika;

import com.groupfour.MedicalCare.Common.db.DbColumnConstants;
import com.groupfour.MedicalCare.Common.db.DbTableConstants;
import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = DbTableConstants.OCENA_KLINIKE)
public class OcenaKlinike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DbColumnConstants.OCENA_KLINIKE_ID)
    private int id;
    @Column(name = DbColumnConstants.OCENA_KLINIKE_OCENA)
    private int ocena;
    @Column(name = DbColumnConstants.OCENA_KLINIKE_AKTIVAN)
    private boolean aktivan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DbColumnConstants.OCENA_KLINIKE_KLINIKA)
    private Klinika klinika;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DbColumnConstants.OCENA_KLINIKE_PACIJENT)
    private Pacijent pacijent;

}
