package com.groupfour.MedicalCare.Model.Osoblje;

import com.groupfour.MedicalCare.Common.db.DbColumnConstants;
import com.groupfour.MedicalCare.Common.db.DbTableConstants;
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

    // Za sada prazno
    @Transient
    private Set<Pacijent> listaPacijenata = new HashSet<>();
    @Transient
    private Set<Operacija> listaOperacija = new HashSet<>();
    @Transient
    private Set<Pregled> listaPregleda = new HashSet<>();
    @Transient
    private Set<Odsustvo> listaOdsusta = new HashSet<>();
    @Transient
    private Set<OcenaLekara> oceneLekara = new HashSet<>();



}
