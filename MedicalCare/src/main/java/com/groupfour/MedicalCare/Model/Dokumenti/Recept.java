package com.groupfour.MedicalCare.Model.Dokumenti;

import com.groupfour.MedicalCare.Common.db.DbColumnConstants;
import com.groupfour.MedicalCare.Common.db.DbTableConstants;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Model.Osoblje.MedicinskaSestra;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = DbTableConstants.RECEPT)
public class Recept {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DbColumnConstants.RECEPT_ID)
    private int id;
    @Column(name = DbColumnConstants.RECEPT_OVEREN)
    private boolean overeno = false;
    @Column(name = DbColumnConstants.RECEPT_ID_LEKA)
    private String idLeka;
    @Column(name = DbColumnConstants.RECEPT_AKTIVAN)
    private boolean aktivan = true;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DbColumnConstants.RECEPT_MEDICINSKA_SESTRA)
    private MedicinskaSestra medicinskaSestra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DbColumnConstants.RECEPT_LEKAR)
    private Lekar lekar;
}
