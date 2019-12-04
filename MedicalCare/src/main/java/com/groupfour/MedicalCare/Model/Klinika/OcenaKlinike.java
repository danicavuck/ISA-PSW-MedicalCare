package com.groupfour.MedicalCare.Model.Klinika;

import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;

public class OcenaKlinike {
    private int id;
    private Klinika klinika;
    private Pacijent pacijent;
    private int ocena;

    public OcenaKlinike(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OcenaKlinike(Klinika klinika, Pacijent pacijent, int ocena) {
        this.klinika = klinika;
        this.pacijent = pacijent;
        this.ocena = ocena;
    }

    public Klinika getKlinika() {
        return klinika;
    }

    public void setKlinika(Klinika klinika) {
        this.klinika = klinika;
    }

    public Pacijent getPacijent() {
        return pacijent;
    }

    public void setPacijent(Pacijent pacijent) {
        this.pacijent = pacijent;
    }

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }

    @Override
    public String toString() {
        return "OcenaKlinike{" +
                "id=" + id +
                ", klinika=" + klinika +
                ", pacijent=" + pacijent +
                ", ocena=" + ocena +
                '}';
    }
}
