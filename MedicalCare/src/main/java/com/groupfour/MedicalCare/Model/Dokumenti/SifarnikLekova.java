package com.groupfour.MedicalCare.Model.Dokumenti;

public class SifarnikLekova {
    private int id;
    public String kodLeka;
    public String nazivLeka;

    public SifarnikLekova(){

    }

    public SifarnikLekova(String kodLeka, String nazivLeka) {
        this.kodLeka = kodLeka;
        this.nazivLeka = nazivLeka;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKodLeka() {
        return kodLeka;
    }

    public void setKodLeka(String kodLeka) {
        this.kodLeka = kodLeka;
    }

    public String getNazivLeka() {
        return nazivLeka;
    }

    public void setNazivLeka(String nazivLeka) {
        this.nazivLeka = nazivLeka;
    }

    @Override
    public String toString() {
        return "SifarnikLekova{" +
                "id=" + id +
                ", kodLeka='" + kodLeka + '\'' +
                ", nazivLeka='" + nazivLeka + '\'' +
                '}';
    }
}
