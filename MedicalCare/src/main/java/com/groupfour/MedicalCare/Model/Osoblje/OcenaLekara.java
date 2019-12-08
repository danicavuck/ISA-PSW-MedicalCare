package com.groupfour.MedicalCare.Model.Osoblje;

import com.groupfour.MedicalCare.Common.db.DbColumnConstants;
import com.groupfour.MedicalCare.Common.db.DbTableConstants;
import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;
import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = DbTableConstants.OCENA_LEKARA)
public class OcenaLekara {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DbColumnConstants.OCENA_LEKARA_ID)
    private int id;
    @Column(name = DbColumnConstants.OCENA_LEKARA_OCENA)
    private int ocena;
    @Column(name = DbColumnConstants.OCENA_LEKARA_AKTIVAN)
    private boolean aktivan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DbColumnConstants.OCENA_LEKARA_ID_LEKARA)
    private Lekar lekar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DbColumnConstants.OCENA_LEKARA_ID_PACIJENTA)
    private Pacijent pacijent;


}
