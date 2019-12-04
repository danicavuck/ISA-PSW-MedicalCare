package com.groupfour.MedicalCare.Model.Dokumenti;

import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;

import java.util.HashSet;
import java.util.Set;

public class Karton {
    private int id;
    private Pacijent pacijet;
    private Set<IzvestajOPregledu> izvestajiOPregledima = new HashSet<>();
    private Set<SifarnikDijagnoza> istorijaBolesti= new HashSet<>();

    public Karton() {}

    public Karton(Pacijent pacijet, Set<IzvestajOPregledu> izvestajiOPregledima, Set<SifarnikDijagnoza> istorijaBolesti) {
        this.pacijet = pacijet;
        this.izvestajiOPregledima = izvestajiOPregledima;
        this.istorijaBolesti = istorijaBolesti;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pacijent getPacijet() {
        return pacijet;
    }

    public void setPacijet(Pacijent pacijet) {
        this.pacijet = pacijet;
    }

    public Set<IzvestajOPregledu> getIzvestajiOPregledima() {
        return izvestajiOPregledima;
    }

    public void setIzvestajiOPregledima(Set<IzvestajOPregledu> izvestajiOPregledima) {
        this.izvestajiOPregledima = izvestajiOPregledima;
    }

    public Set<SifarnikDijagnoza> getIstorijaBolesti() {
        return istorijaBolesti;
    }

    public void setIstorijaBolesti(Set<SifarnikDijagnoza> istorijaBolesti) {
        this.istorijaBolesti = istorijaBolesti;
    }

    @Override
    public String toString() {
        return "Karton{" +
                "id=" + id +
                ", pacijet=" + pacijet +
                ", izvestajiOPregledima=" + izvestajiOPregledima +
                ", istorijaBolesti=" + istorijaBolesti +
                '}';
    }
}
