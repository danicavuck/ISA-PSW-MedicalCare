package com.groupfour.MedicalCare.Model.Dokumenti;

import com.groupfour.MedicalCare.Common.db.DbColumnConstants;
import com.groupfour.MedicalCare.Common.db.DbTableConstants;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = DbTableConstants.IZVESTAJ_O_PREGLEDU)
public class IzvestajOPregledu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DbColumnConstants.IZVESTAJ_O_PREGLEDU_ID)
    private int id;
    @Column(name = DbColumnConstants.IZVESTAJ_O_PREGLEDU_INFORMACIJE)
    private String informacijeOPregledu;
    @Column(name = DbColumnConstants.IZVESTAJ_O_PREGLEDU_AKTIVAN)
    private boolean aktivan = true;

    @Column(name = DbColumnConstants.IZVESTAJ_O_PREGLEDU_PACIJENT_ID)
    private int pacijentId;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = DbTableConstants.IZVESTAJ_RECEPT,
            joinColumns = @JoinColumn(name = DbColumnConstants.IZVESTAJ_O_PREGLEDU_ID),
            inverseJoinColumns = @JoinColumn(name = DbColumnConstants.RECEPT_ID)
    )
    private Set<Recept> recepti = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = DbColumnConstants.IZVESTAJ_O_PREGLEDU_DIJAGNOZA)
    private SifarnikDijagnoza sifarnikDijagnoza;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = DbColumnConstants.IZVESTAJ_O_PREGLEDU_LEKAR)
    private Lekar lekar;


}
