package com.groupfour.MedicalCare.Model.Klinika;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.groupfour.MedicalCare.Common.db.DbColumnConstants;
import com.groupfour.MedicalCare.Common.db.DbTableConstants;
import com.groupfour.MedicalCare.Model.Pregled.Pregled;
import com.groupfour.MedicalCare.Model.Zahtevi.Operacija;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = DbTableConstants.SALA)
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DbColumnConstants.SALA_ID)
    private int id;
    @Column(name = DbColumnConstants.SALA_ZAUZECE)
    private boolean zauzeta = false;
    @Column(name = DbColumnConstants.SALA_POCETAK_TERMINA)
    private LocalDateTime pocetakTermina;
    @Column(name = DbColumnConstants.SALA_ZAVRSETAK_TERMINA)
    private LocalDateTime krajTermina;
    @Column(name = DbColumnConstants.SALA_BROJ_SALE)
    private int brojSale;

    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("sala")
    private Set<Operacija> operacije = new HashSet<>();

    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("sala")
    private Set<Pregled> pregledi = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = DbColumnConstants.SALA_KLINIKA)
    @JsonIgnoreProperties("spisakSala")
    private Klinika klinika;

    @Column(name = DbColumnConstants.SALA_AKTIVNA)
    private boolean aktivna;

    @Override
    public String toString() {
        return "Sala id:" + this.id + " Broj sale: " + this.brojSale + " Pocetak termina: " + this.pocetakTermina.toString() + " Kraj termina: " + this.krajTermina;
    }
}
