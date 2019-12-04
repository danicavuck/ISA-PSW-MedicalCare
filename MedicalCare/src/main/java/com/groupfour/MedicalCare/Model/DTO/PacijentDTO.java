package com.groupfour.MedicalCare.Model.DTO;

import org.springframework.stereotype.Component;

@Component
public class PacijentDTO {
    private String email;
    private String lozinka;
    private String ime;
    private String prezime;
    private String adresaPrebivalista;
    private String grad;
    private String drzava;
    private String telefon;
    private String brojOsiguranja;

    public PacijentDTO() {
    }

    public PacijentDTO(String email, String lozinka) {
        this.email = email;
        this.lozinka = lozinka;
    }

    public PacijentDTO(String email, String lozinka, String ime, String prezime, String adresaPrebivalista, String grad, String drzava, String telefon, String brojOsiguranja) {
        this.email = email;
        this.lozinka = lozinka;
        this.ime = ime;
        this.prezime = prezime;
        this.adresaPrebivalista = adresaPrebivalista;
        this.grad = grad;
        this.drzava = drzava;
        this.telefon = telefon;
        this.brojOsiguranja = brojOsiguranja;
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

    public String getAdresaPrebivalista() {
        return adresaPrebivalista;
    }

    public void setAdresaPrebivalista(String adresaPrebivalista) {
        this.adresaPrebivalista = adresaPrebivalista;
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

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getBrojOsiguranja() {
        return brojOsiguranja;
    }

    public void setBrojOsiguranja(String brojOsiguranja) {
        this.brojOsiguranja = brojOsiguranja;
    }

    @Override
    public String toString() {
        return "PacijentDTO{" +
                "email='" + email + '\'' +
                ", lozinka='" + lozinka + '\'' +
                ", ime='" + ime + '\'' +
                ", prezime='" + prezime + '\'' +
                ", adresaPrebivalista='" + adresaPrebivalista + '\'' +
                ", grad='" + grad + '\'' +
                ", drzava='" + drzava + '\'' +
                ", telefon='" + telefon + '\'' +
                ", brojOsiguranja='" + brojOsiguranja + '\'' +
                '}';
    }
}
