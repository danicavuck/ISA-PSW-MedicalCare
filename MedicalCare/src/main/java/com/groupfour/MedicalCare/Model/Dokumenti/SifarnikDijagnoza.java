package com.groupfour.MedicalCare.Model.Dokumenti;

public class SifarnikDijagnoza {

    int id;
    String kodBolesti;
    String nazivBolesti;

    public SifarnikDijagnoza(){

    }

    public SifarnikDijagnoza(String kodBolesti, String nazivBolesti) {
        this.kodBolesti = kodBolesti;
        this.nazivBolesti = nazivBolesti;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKodBolesti() {
        return kodBolesti;
    }

    public void setKodBolesti(String kodBolesti) {
        this.kodBolesti = kodBolesti;
    }

    public String getNazivBolesti() {
        return nazivBolesti;
    }

    public void setNazivBolesti(String nazivBolesti) {
        this.nazivBolesti = nazivBolesti;
    }

    @Override
    public String toString() {
        return "SifarnikDijagnoza{" +
                "id=" + id +
                ", kodBolesti='" + kodBolesti + '\'' +
                ", nazivBolesti='" + nazivBolesti + '\'' +
                '}';
    }
}
