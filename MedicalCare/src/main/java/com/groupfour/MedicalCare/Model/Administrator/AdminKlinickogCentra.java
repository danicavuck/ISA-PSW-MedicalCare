package com.groupfour.MedicalCare.Model.Administrator;

import com.groupfour.MedicalCare.Common.db.DbColumnConstants;
import com.groupfour.MedicalCare.Common.db.DbTableConstants;
import com.groupfour.MedicalCare.Model.Dokumenti.SifarnikDijagnoza;
import com.groupfour.MedicalCare.Model.Dokumenti.SifarnikLekova;
import com.groupfour.MedicalCare.Model.Zahtevi.RegistracijaPacijenta;
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
@Table(name = DbTableConstants.ADMINKLINICKOGCENTRA)
public class AdminKlinickogCentra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DbColumnConstants.ADMIN_KC_ID)
    private int id;
    @Column(name = DbColumnConstants.ADMIN_KC_EMAIL)
    private String email;
    @Column(name = DbColumnConstants.ADMIN_KC_IME)
    private String ime;
    @Column(name = DbColumnConstants.ADMIN_KC_PREZIME)
    private String prezime;
    @Column(name = DbColumnConstants.ADMIN_KC_LOZINKA)
    private String lozinka;

    @Column(name = DbColumnConstants.ADMIN_KC_PRVI_PUT)
    private boolean prviPutLogovan = true;

    @Column(name = DbColumnConstants.ADMIN_KC_AKTIVAN)
    private boolean aktivan = true;

    @OneToMany(mappedBy = "adminKlinickogCentra", cascade = CascadeType.ALL)
    private Set<RegistracijaPacijenta> listaRegistracija = new HashSet<>();


    public void dodajRegistraciju(RegistracijaPacijenta registracijaPacijenta){
        if(registracijaPacijenta != null){
            listaRegistracija.add(registracijaPacijenta);
            registracijaPacijenta.setAdminKlinickogCentra(this);
        }
    }

}
