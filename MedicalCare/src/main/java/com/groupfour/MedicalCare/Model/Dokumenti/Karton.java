package com.groupfour.MedicalCare.Model.Dokumenti;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.groupfour.MedicalCare.Common.db.DbColumnConstants;
import com.groupfour.MedicalCare.Common.db.DbTableConstants;
import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;
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
@Table(name = DbTableConstants.KARTON)
public class Karton {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DbColumnConstants.KARTON_ID)
    private int id;
    @Column(name = DbColumnConstants.KARTON_AKTIVAN)
    private boolean aktivan;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "zdravstveniKarton", cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonIgnoreProperties("zdravstveniKarton")
    private Pacijent pacijent;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name = DbTableConstants.KARTON_IZVESTAJI,
            joinColumns = @JoinColumn(name = DbColumnConstants.KARTON_ID),
            inverseJoinColumns = @JoinColumn(name = DbColumnConstants.IZVESTAJ_O_PREGLEDU_ID)
    )
    private Set<IzvestajOPregledu> izvestajiOPregledima = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name = DbTableConstants.KARTON_DIJAGNOZE,
            joinColumns = @JoinColumn(name = DbColumnConstants.KARTON_ID),
            inverseJoinColumns = @JoinColumn(name = DbColumnConstants.SIFARNIK_DIJAGNOZA_ID)
    )
    private Set<SifarnikDijagnoza> istorijaBolesti = new HashSet<>();


    public void dodajDijagnozu(SifarnikDijagnoza dijagnoza){
        istorijaBolesti.add(dijagnoza);
    }

    public void dodajIzvestaj(IzvestajOPregledu izvestajOPregledu){
        izvestajiOPregledima.add(izvestajOPregledu);
    }




}
