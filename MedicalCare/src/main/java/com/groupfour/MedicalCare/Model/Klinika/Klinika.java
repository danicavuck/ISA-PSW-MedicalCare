package com.groupfour.MedicalCare.Model.Klinika;

import com.groupfour.MedicalCare.Common.db.DbColumnConstants;
import com.groupfour.MedicalCare.Common.db.DbTableConstants;
import com.groupfour.MedicalCare.Model.Administrator.AdminKlinike;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
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
@Table(name = DbTableConstants.KLINIKA)
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
    private Set<Lekar> listaLekara = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = DbColumnConstants.SALA_ID)
    private Set<Sala>  spisakSala = new HashSet<>();

    @OneToMany(mappedBy = "klinika")
    private Set<OcenaKlinike> oceneKlinike;

    @OneToMany(mappedBy = "klinika", cascade = CascadeType.ALL)
    private Set<AdminKlinike> adminiKlinike = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = DbTableConstants.PACIJENT_KLINIKA,
            joinColumns = @JoinColumn(name = DbColumnConstants.KLINIKA_ID),
            inverseJoinColumns = @JoinColumn(name = DbColumnConstants.PACIJENT_ID)
    )
    private Set<Pacijent> pacijenti = new HashSet<>();

    public void dodajAdminaKlinike(AdminKlinike adminKlinike){
        this.adminiKlinike.add(adminKlinike);
    }

}
