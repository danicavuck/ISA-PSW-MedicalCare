package com.groupfour.MedicalCare.Model.Administrator;

import com.groupfour.MedicalCare.Model.Klinika.Klinika;

public class AdminKlinike {
    private String email;
    private String lozinka;
    private String ime;
    private String prezime;
    private Klinika klinika;

    public AdminKlinike() {
    }

    public AdminKlinike(String email, String lozinka, String ime, String prezime, Klinika klinika) {
        this.email = email;
        this.lozinka = lozinka;
        this.ime = ime;
        this.prezime = prezime;
        this.klinika = klinika;
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

    public Klinika getKlinika() {
        return klinika;
    }

    public void setKlinika(Klinika klinika) {
        this.klinika = klinika;
    }

    @Override
    public String toString() {
        return "AdminKlinike{" +
                "email='" + email + '\'' +
                ", lozinka='" + lozinka + '\'' +
                ", ime='" + ime + '\'' +
                ", prezime='" + prezime + '\'' +
                ", klinika=" + klinika +
                '}';
    }
}
