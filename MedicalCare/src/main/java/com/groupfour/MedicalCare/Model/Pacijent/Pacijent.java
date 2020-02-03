package com.groupfour.MedicalCare.Model.Pacijent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.groupfour.MedicalCare.Common.db.DbColumnConstants;
import com.groupfour.MedicalCare.Common.db.DbTableConstants;
import com.groupfour.MedicalCare.Model.Dokumenti.Karton;
import com.groupfour.MedicalCare.Model.Klinika.Klinika;
import com.groupfour.MedicalCare.Model.Klinika.OcenaKlinike;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Model.Osoblje.MedicinskaSestra;
import com.groupfour.MedicalCare.Model.Osoblje.OcenaLekara;
import com.groupfour.MedicalCare.Model.Pregled.Operacija;
import com.groupfour.MedicalCare.Model.Pregled.Pregled;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = DbTableConstants.PACIJENT)
@JsonIgnoreProperties({ "klinika", "lozinka", "listaPregleda"})
public class Pacijent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DbColumnConstants.PACIJENT_ID)
    private int id;
    @Column(name = DbColumnConstants.PACIJENT_EMAIL)
    private String email;
    @Column(name = DbColumnConstants.PACIJENT_LOZINKA)
    private String lozinka;
    @Column(name = DbColumnConstants.PACIJENT_IME)
    private String ime;
    @Column(name = DbColumnConstants.PACIJENT_PREZIME)
    private String prezime;
    @Column(name = DbColumnConstants.PACIJENT_ADRESA)
    private String adresa;
    @Column(name = DbColumnConstants.PACIJENT_GRAD)
    private String grad;
    @Column(name = DbColumnConstants.PACIJENT_DRZAVA)
    private String drzava;
    @Column(name = DbColumnConstants.PACIJENT_TELEFON)
    private String brojTelefona;
    @Column(name = DbColumnConstants.PACIJENT_BROJOSIGURANJA)
    private String brojOsiguranja;
    @Column(name = DbColumnConstants.PACIJENT_PRVO_LOGOVANJE)
    private boolean prvoLogovanje;

    @ManyToMany
    @JoinTable(
            name = DbTableConstants.LEKAR_PACIJENT,
            joinColumns = @JoinColumn(name = DbColumnConstants.LEKAR_PACIJENT_PACIJENT),
            inverseJoinColumns = @JoinColumn(name = DbColumnConstants.LEKAR_PACIJENT_LEKAR)
    )
    private Set<Lekar> listaLekara = new HashSet<>();


    @ManyToMany
    @JoinTable(
            name = DbTableConstants.MED_SESTRA_PACIJENT,
            joinColumns = @JoinColumn(name = DbColumnConstants.MEDICINSKA_SESTRA_PACIJENT),
            inverseJoinColumns = @JoinColumn(name = DbColumnConstants.MEDICINSKA_SESTRA_SESTRA)
    )
    private Set<MedicinskaSestra> listaSestara = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pacijent", cascade = CascadeType.ALL)
    private Set<Pregled> listaPregleda = new HashSet<>();

    @OneToMany(mappedBy = "pacijent", cascade = CascadeType.ALL)
    private Set<Operacija> listaOperacija = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = DbColumnConstants.PACIJENT_KARTON)
    private Karton zdravstveniKarton;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = DbTableConstants.PACIJENT_KLINIKA,
            joinColumns = @JoinColumn(name = DbColumnConstants.PACIJENT_ID),
            inverseJoinColumns = @JoinColumn(name = DbColumnConstants.KLINIKA_ID)
    )
    private Set<Klinika> klinika = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pacijent")
    private Set<OcenaLekara> oceneLekara = new HashSet<>();

    @OneToMany(mappedBy = "pacijent", cascade = CascadeType.ALL)
    private Set<OcenaKlinike> oceneKlinike = new HashSet<>();


    public void dodajKarton(Karton karton) {
        this.zdravstveniKarton = karton;
        karton.setPacijent(this);
    }
}
