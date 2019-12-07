package com.groupfour.MedicalCare.Model.Osoblje;

import com.groupfour.MedicalCare.Common.db.DbColumnConstants;
import com.groupfour.MedicalCare.Common.db.DbTableConstants;
import com.groupfour.MedicalCare.Model.Dokumenti.Recept;
import com.groupfour.MedicalCare.Model.Klinika.Klinika;
import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;
import com.groupfour.MedicalCare.Model.Pregled.Pregled;
import com.groupfour.MedicalCare.Model.Zahtevi.Odsustvo;
import com.groupfour.MedicalCare.Model.Zahtevi.Operacija;
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
@Table(name = DbTableConstants.LEKAR)
public class Lekar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DbColumnConstants.LEKAR_ID)
    private int id;
    @Column(name = DbColumnConstants.LEKAR_IME)
    private String ime;
    @Column(name = DbColumnConstants.LEKAR_PREZIME)
    private String prezime;
    @Column(name = DbColumnConstants.LEKAR_EMAIL)
    private String email;
    @Column(name = DbColumnConstants.LEKAR_LOZINKA)
    private String lozinka;
    @Column(name = DbColumnConstants.LEKAR_PROSECNA_OCENA)
    private float prosecnaOcena;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DbColumnConstants.LEKAR_KLINIKA)
    private Klinika klinika;

    @ManyToMany
    @JoinTable(
            name = DbTableConstants.LEKAR_PACIJENT,
            joinColumns = @JoinColumn(name = DbColumnConstants.LEKAR_PACIJENT_LEKAR),
            inverseJoinColumns = @JoinColumn(name = DbColumnConstants.LEKAR_PACIJENT_PACIJENT)
    )
    private Set<Pacijent> listaPacijenata = new HashSet<>();


    @ManyToMany
    @JoinTable(
            name = DbTableConstants.LEKAR_OPERACIJA,
            joinColumns = @JoinColumn(name = DbColumnConstants.LEKAR_ID),
            inverseJoinColumns = @JoinColumn(name = DbColumnConstants.OPERACIJA_ID)
    )
    private Set<Operacija> listaOperacija = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = DbTableConstants.LEKAR_PREGLED,
            joinColumns = @JoinColumn(name = DbColumnConstants.LEKAR_ID),
            inverseJoinColumns = @JoinColumn(name = DbColumnConstants.PREGLED_ID)
    )
    private Set<Pregled> listaPregleda = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = DbTableConstants.LEKAR_ODSUSTVA,
            joinColumns = @JoinColumn(name = DbColumnConstants.LEKAR_ID),
            inverseJoinColumns = @JoinColumn(name = DbColumnConstants.ODSUSTVO_ID)
    )
    private Set<Odsustvo> listaOdsusta = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lekar")
    private Set<OcenaLekara> oceneLekara = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lekar")
    private Set<Recept> recepti = new HashSet<>();

    public void dodajPacijenta(Pacijent pacijent){
        this.listaPacijenata.add(pacijent);
        pacijent.dodajLekara(this);
    }
}
