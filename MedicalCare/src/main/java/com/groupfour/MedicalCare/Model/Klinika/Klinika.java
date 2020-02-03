package com.groupfour.MedicalCare.Model.Klinika;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.groupfour.MedicalCare.Common.db.DbColumnConstants;
import com.groupfour.MedicalCare.Common.db.DbTableConstants;
import com.groupfour.MedicalCare.Model.Administrator.AdminKlinike;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Model.Osoblje.MedicinskaSestra;
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
@Entity
@Table(name = DbTableConstants.KLINIKA)
@JsonIgnoreProperties({"adminiKlinike", "listaLekara", "spisakSala"})
public class Klinika {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DbColumnConstants.KLINIKA_ID)
    private int id;
    @Column(name = DbColumnConstants.KLINIKA_NAZIV)
    private String naziv;
    @Column(name = DbColumnConstants.KLINIKA_ADRESA)
    private String adresa;
    @Column(name = DbColumnConstants.KLINIKA_OPIS)
    private String opis;
    @Column(name = DbColumnConstants.KLINIKA_PROSECNA_OCENA)
    private float prosecnaOcena;

    @OneToMany(mappedBy = "klinika", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("klinika")
    private Set<Lekar> listaLekara = new HashSet<>();

    @OneToMany(mappedBy = "klinika", cascade = CascadeType.ALL)
    private Set<MedicinskaSestra> listaSestara = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = DbTableConstants.KLINIKA_SALA,
            joinColumns = @JoinColumn(name = DbColumnConstants.KLINIKA_ID),
            inverseJoinColumns = @JoinColumn(name = DbColumnConstants.SALA_ID)
    )
    @JsonIgnoreProperties("klinika")
    private Set<Sala> spisakSala = new HashSet<>();

    @OneToMany(mappedBy = "klinika", cascade = CascadeType.ALL)
    private Set<OcenaKlinike> oceneKlinike;

    @OneToMany(mappedBy = "klinika", cascade = CascadeType.ALL)
    private Set<AdminKlinike> adminiKlinike = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinTable(
            name = DbTableConstants.PACIJENT_KLINIKA,
            joinColumns = @JoinColumn(name = DbColumnConstants.KLINIKA_ID),
            inverseJoinColumns = @JoinColumn(name = DbColumnConstants.PACIJENT_ID)
    )
    @JsonIgnoreProperties("pacijenti")
    private Set<Pacijent> pacijenti = new HashSet<>();

    public void dodajLekara(Lekar lekar) {
        this.listaLekara.add(lekar);
        lekar.setKlinika(this);
    }

    public void dodajMedicinskuSestru(MedicinskaSestra sestra) {
        this.listaSestara.add(sestra);
        sestra.setKlinika(this);
    }

    public void dodajSalu(Sala sala) {
        this.spisakSala.add(sala);
        sala.setKlinika(this);
    }
    public void dodajAdmina(AdminKlinike admin) {
        this.adminiKlinike.add(admin);
        admin.setKlinika(this);
    }

    public void dodajOcenuKlinike(OcenaKlinike ocenaKlinike) {
        this.oceneKlinike.add(ocenaKlinike);
        ocenaKlinike.setKlinika(this);
    }

    public void dodajPacijenta(Pacijent p) {
        this.getPacijenti().add(p);
        p.getKlinika().add(this);
    }

    @Override
    public String toString() {
        return "Naziv:" + this.naziv + " Adresa:" + this.adresa + " Opis:" + this.opis + " Prosecna ocena:" + this.prosecnaOcena + " Lekari: " + this.listaLekara;
    }
}
