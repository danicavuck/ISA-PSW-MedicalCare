package com.groupfour.MedicalCare.Model.Klinika;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.groupfour.MedicalCare.Common.db.DbColumnConstants;
import com.groupfour.MedicalCare.Common.db.DbTableConstants;
import com.groupfour.MedicalCare.Model.Pregled.Operacija;
import com.groupfour.MedicalCare.Model.Pregled.Pregled;
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
@Table(name = DbTableConstants.SALA)
@JsonIgnoreProperties({"klinika"})
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DbColumnConstants.SALA_ID)
    private int id;
    @Column(name = DbColumnConstants.SALA_ZAUZECE)
    private boolean zauzeta = false;
    @Column(name = DbColumnConstants.SALA_NAZIV_SALE)
    private String nazivSale;

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
        return "Sala id:" + this.id + " Naziv sale: " + this.nazivSale;
    }
}
