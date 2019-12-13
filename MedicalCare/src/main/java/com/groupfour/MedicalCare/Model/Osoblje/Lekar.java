package com.groupfour.MedicalCare.Model.Osoblje;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.groupfour.MedicalCare.Common.db.DbColumnConstants;
import com.groupfour.MedicalCare.Common.db.DbTableConstants;
import com.groupfour.MedicalCare.Model.Dokumenti.Recept;
import com.groupfour.MedicalCare.Model.Klinika.Klinika;
import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;
import com.groupfour.MedicalCare.Model.Pregled.Pregled;
import com.groupfour.MedicalCare.Model.Zahtevi.Odsustvo;
import com.groupfour.MedicalCare.Model.Zahtevi.Operacija;
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
    private Set<Pregled> listaPregleda = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = DbTableConstants.LEKAR_ODSUSTVA,
            joinColumns = @JoinColumn(name = DbColumnConstants.LEKAR_ID),
            inverseJoinColumns = @JoinColumn(name = DbColumnConstants.ODSUSTVO_ID)
    )
    private Set<Odsustvo> listaOdsusta = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lekar", cascade = CascadeType.ALL)
    private Set<OcenaLekara> oceneLekara = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lekar", cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<Recept> recepti = new HashSet<>();

    public void dodajPacijenta(Pacijent pacijent){
        this.listaPacijenata.add(pacijent);
        pacijent.getListaLekara().add(this);
    }

    public void dodajRecept(Recept recept){
        this.recepti.add(recept);
        recept.setLekar(this);
    }

    public void dodajOperaciju(Operacija operacija){
        this.listaOperacija.add(operacija);
        operacija.getLekar().add(this);
    }

    public void dodajPregled(Pregled pregled){
        this.listaPregleda.add(pregled);
        pregled.getLekari().add(this);
    }

    public void dodajOcenuLekara(OcenaLekara ocenaLekara){
        this.oceneLekara.add(ocenaLekara);
        ocenaLekara.setLekar(this);
    }

    @Override
    public String toString() {
        return "Ime: " + this.ime + " Prezime: " + this.prezime + " Email:" + this.email + " Prosecna ocena: " + this.prosecnaOcena;
    }

    @JsonIgnore
    public String getLozinka(){
        return this.lozinka;
    }
}
