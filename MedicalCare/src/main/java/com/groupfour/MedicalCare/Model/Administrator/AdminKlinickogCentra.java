package com.groupfour.MedicalCare.Model.Administrator;

import com.groupfour.MedicalCare.Common.db.DbColumnConstants;
import com.groupfour.MedicalCare.Common.db.DbTableConstants;
import com.groupfour.MedicalCare.Model.Dokumenti.SifarnikDijagnoza;
import com.groupfour.MedicalCare.Model.Dokumenti.SifarnikLekova;
import com.groupfour.MedicalCare.Model.Zahtevi.RegistracijaPacijenta;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = DbTableConstants.ADMINKLINICKOGCENTRA)
public class AdminKlinickogCentra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DbColumnConstants.ADMIN_KC_ID)
    private int id;
    @Column(name = DbColumnConstants.ADMIN_KC_EMAIL)
    private String email;
    @Column(name = DbColumnConstants.ADMIN_KC_LOZINKA)
    private String lozinka;
    @Column(name = DbColumnConstants.ADMIN_KC_IME)
    private String ime;
    @Column(name = DbColumnConstants.ADMIN_KC_PREZIME)
    private String prezime;

    @OneToMany(mappedBy = "adminKlinickogCentra", cascade = CascadeType.ALL)
    private Set<RegistracijaPacijenta> listaRegistracija = new HashSet<>();
    @Transient
    private SifarnikDijagnoza sifarnikDijagnoza;
    @Transient
    private SifarnikLekova sifarnikLekova;

    public AdminKlinickogCentra() {
    }

    public AdminKlinickogCentra(String email, String lozinka, String ime, String prezime) {
        this.email = email;
        this.lozinka = lozinka;
        this.ime = ime;
        this.prezime = prezime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<RegistracijaPacijenta> getListaRegistracija() {
        return listaRegistracija;
    }

    public void setListaRegistracija(Set<RegistracijaPacijenta> listaRegistracija) {
        this.listaRegistracija = listaRegistracija;
    }

    public SifarnikDijagnoza getSifarnikDijagnoza() {
        return sifarnikDijagnoza;
    }

    public void setSifarnikDijagnoza(SifarnikDijagnoza sifarnikDijagnoza) {
        this.sifarnikDijagnoza = sifarnikDijagnoza;
    }

    public SifarnikLekova getSifarnikLekova() {
        return sifarnikLekova;
    }

    public void setSifarnikLekova(SifarnikLekova sifarnikLekova) {
        this.sifarnikLekova = sifarnikLekova;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public void dodajRegistraciju(RegistracijaPacijenta registracijaPacijenta){
        if(registracijaPacijenta != null){
            listaRegistracija.add(registracijaPacijenta);
            registracijaPacijenta.setAdminKlinickogCentra(this);
        }
    }

}
