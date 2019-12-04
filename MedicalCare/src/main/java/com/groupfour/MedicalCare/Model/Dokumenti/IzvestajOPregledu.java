package com.groupfour.MedicalCare.Model.Dokumenti;

import java.util.HashSet;
import java.util.Set;

public class IzvestajOPregledu {
    private int id;
    private String informacijeOPregledu;
    private Set<Recept> recepti = new HashSet<>();
    private SifarnikDijagnoza sifarnikDijagnoza;
    private SifarnikLekova sifarnikLekova;

    public IzvestajOPregledu() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInformacijeOPregledu() {
        return informacijeOPregledu;
    }

    public void setInformacijeOPregledu(String informacijeOPregledu) {
        this.informacijeOPregledu = informacijeOPregledu;
    }

    public Set<Recept> getRecepti() {
        return recepti;
    }

    public void setRecepti(Set<Recept> recepti) {
        this.recepti = recepti;
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

    @Override
    public String toString() {
        return "IzvestajOPregledu{" +
                "id=" + id +
                ", informacijeOPregledu='" + informacijeOPregledu + '\'' +
                ", recepti=" + recepti +
                ", sifarnikDijagnoza=" + sifarnikDijagnoza +
                ", sifarnikLekova=" + sifarnikLekova +
                '}';
    }
}
