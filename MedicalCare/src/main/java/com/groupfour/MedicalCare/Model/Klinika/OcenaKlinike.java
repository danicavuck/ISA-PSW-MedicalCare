package com.groupfour.MedicalCare.Model.Klinika;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = DbColumnConstants.OCENA_KLINIKE_KLINIKA)
    private Klinika klinika;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = DbColumnConstants.OCENA_KLINIKE_PACIJENT)
    @JsonIgnoreProperties("oceneKlinike")
    private Pacijent pacijent;

}
