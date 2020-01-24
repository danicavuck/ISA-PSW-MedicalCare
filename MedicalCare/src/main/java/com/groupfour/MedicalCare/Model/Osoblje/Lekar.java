package com.groupfour.MedicalCare.Model.Osoblje;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.groupfour.MedicalCare.Common.db.DbColumnConstants;
import com.groupfour.MedicalCare.Common.db.DbTableConstants;
import com.groupfour.MedicalCare.Model.Dokumenti.Recept;
import com.groupfour.MedicalCare.Model.Klinika.Klinika;
import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;
import com.groupfour.MedicalCare.Model.Pregled.Operacija;
import com.groupfour.MedicalCare.Model.Pregled.Pregled;
import com.groupfour.MedicalCare.Model.Pregled.PreglediNaCekanju;
import com.groupfour.MedicalCare.Model.Zahtevi.OdsustvoLekara;
import lombok.*;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = DbTableConstants.LEKAR)
@JsonIgnoreProperties({"lozinka"})
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
    @JsonIgnore
    private String lozinka;
    @Column(name = DbColumnConstants.LEKAR_PROSECNA_OCENA)
    private float prosecnaOcena;
    @Column(name = DbColumnConstants.LEKAR_AKTIVAN)
    private boolean aktivan;
    @Column(name = DbColumnConstants.LEKAR_PRVO_LOGOVANJE)
    private boolean prvoLogovanje;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = DbColumnConstants.LEKAR_KLINIKA)
    @JsonIgnoreProperties("listaLekara")
    private Klinika klinika;

    @ManyToMany
    @JoinTable(
            name = DbTableConstants.LEKAR_PACIJENT,
            joinColumns = @JoinColumn(name = DbColumnConstants.LEKAR_PACIJENT_LEKAR),
            inverseJoinColumns = @JoinColumn(name = DbColumnConstants.LEKAR_PACIJENT_PACIJENT)
    )
    @JsonIgnoreProperties("listaLekara")
    private Set<Pacijent> listaPacijenata = new HashSet<>();


    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = DbTableConstants.LEKAR_OPERACIJA,
            joinColumns = @JoinColumn(name = DbColumnConstants.LEKAR_ID),
            inverseJoinColumns = @JoinColumn(name = DbColumnConstants.OPERACIJA_ID)
    )
    private Set<Operacija> listaOperacija = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = DbTableConstants.LEKAR_PREGLED,
            joinColumns = @JoinColumn(name = DbColumnConstants.LEKAR_ID),
            inverseJoinColumns = @JoinColumn(name = DbColumnConstants.PREGLED_ID)
    )
    private Set<Pregled> setPregleda = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "lekar")
    private Set<OdsustvoLekara> listaOdsusta = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lekar", cascade = CascadeType.ALL)
    private Set<OcenaLekara> oceneLekara = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lekar", cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<Recept> recepti = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lekar", cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<PreglediNaCekanju> preglediNaCekanju = new HashSet<>();


    public void dodajPregled(Pregled pregled) {
        this.setPregleda.add(pregled);
        pregled.dodajLekara(this);
    }

    @Override
    public String toString() {
        return "Ime: " + this.ime + " Prezime: " + this.prezime + " Email:" + this.email + " Prosecna ocena: " + this.prosecnaOcena;
    }

}
