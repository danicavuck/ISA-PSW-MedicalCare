package com.groupfour.MedicalCare.Model.Zahtevi;

import com.groupfour.MedicalCare.Model.Administrator.AdminKlinickogCentra;
import com.groupfour.MedicalCare.Common.db.DbColumnConstants;
import com.groupfour.MedicalCare.Common.db.DbTableConstants;

import javax.persistence.*;

@Entity
@Table(name = DbTableConstants.REGISTRACIJAPACIJENATA)
public class RegistracijaPacijenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DbColumnConstants.REGISTRACIJA_ID)
    private int id;
    @Column(name = DbColumnConstants.REGISTRACIJA_EMAIL)
    private String email;
    @Column(name = DbColumnConstants.REGISTRACIJA_LOZINKA)
    private String lozinka;
    @Column(name = DbColumnConstants.REGISTRACIJA_IME)
    private String ime;
    @Column(name = DbColumnConstants.REGISTRACIJA_PREZIME)
    private String prezime;
    @Column(name = DbColumnConstants.REGISTRACIJA_ADRESA)
    private String adresa;
    @Column(name = DbColumnConstants.REGISTRACIJA_GRAD)
    private String grad;
    @Column(name = DbColumnConstants.REGISTRACIJA_DRZAVA)
    private String drzava;
    @Column(name = DbColumnConstants.REGISTRACIJA_TELEFON)
    private String brojTelefona;
    @Column(name = DbColumnConstants.REGISTRACIJA_OSIGURANJE)
    private String brojOsiguranja;
    @Column(name = DbColumnConstants.REGISTRACIJA_ODOBREN)
    private boolean odobren = false;
    @Column(name = DbColumnConstants.REGISTRACIJA_RAZLOG_ODBIJANJA)
    private String razlogOdbijanja;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DbColumnConstants.REGISTRACIJA_ADMIN_KC)
    private AdminKlinickogCentra adminKlinickogCentra;

    public RegistracijaPacijenta() {
    }

    public RegistracijaPacijenta(String email, String lozinka, String ime, String prezime, String adresa, String grad, String drzava, String brojTelefona, String brojOsiguranja) {
        this.email = email;
        this.lozinka = lozinka;
        this.ime = ime;
        this.prezime = prezime;
        this.adresa = adresa;
        this.grad = grad;
        this.drzava = drzava;
        this.brojTelefona = brojTelefona;
        this.brojOsiguranja = brojOsiguranja;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getDrzava() {
        return drzava;
    }

    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
    }

    public String getBrojOsiguranja() {
        return brojOsiguranja;
    }

    public void setBrojOsiguranja(String brojOsiguranja) {
        this.brojOsiguranja = brojOsiguranja;
    }

    public boolean isOdobren() {
        return odobren;
    }

    public void setOdobren(boolean odobren) {
        this.odobren = odobren;
    }

    public String getRazlogOdbijanja() {
        return razlogOdbijanja;
    }

    public void setRazlogOdbijanja(String razlogOdbijanja) {
        this.razlogOdbijanja = razlogOdbijanja;
    }

    public AdminKlinickogCentra getAdminKlinickogCentra() {
        return adminKlinickogCentra;
    }

    public void setAdminKlinickogCentra(AdminKlinickogCentra adminKlinickogCentra) {
        this.adminKlinickogCentra = adminKlinickogCentra;
    }

}
