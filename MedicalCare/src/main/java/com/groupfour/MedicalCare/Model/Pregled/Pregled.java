package com.groupfour.MedicalCare.Model.Pregled;

import com.groupfour.MedicalCare.Model.Dokumenti.IzvestajOPregledu;
import com.groupfour.MedicalCare.Model.Klinika.Sala;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;

import java.time.LocalDateTime;


public class Pregled {
    private int id;
    private LocalDateTime terminPregleda;
    private String tipPregleda;
    private String trajanjePregleda;
    private Sala sala;
    private Lekar lekar;
    private int cena;
    private IzvestajOPregledu izvestajOPregledu;
    private int popust;


    public Pregled() {

    }

    public int getPopust() {
        return popust;
    }

    public void setPopust(int popust) {
        this.popust = popust;
    }

    public IzvestajOPregledu getIzvestajOPregledu() {
        return izvestajOPregledu;
    }

    public void setIzvestajOPregledu(IzvestajOPregledu izvestajOPregledu) {
        this.izvestajOPregledu = izvestajOPregledu;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipPregleda() {
        return tipPregleda;
    }

    public void setTipPregleda(String tipPregleda) {
        this.tipPregleda = tipPregleda;
    }

    public String getTrajanjePregleda() {
        return trajanjePregleda;
    }

    public void setTrajanjePregleda(String trajanjePregleda) {
        this.trajanjePregleda = trajanjePregleda;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getTerminPregleda() {
        return terminPregleda;
    }

    public void setTerminPregleda(LocalDateTime terminPregleda) {
        this.terminPregleda = terminPregleda;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Lekar getLekar() {
        return lekar;
    }

    public void setLekar(Lekar lekar) {
        this.lekar = lekar;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    @Override
    public String toString() {
        return "Pregled{" +
                "id=" + id +
                ", terminPregleda=" + terminPregleda +
                ", tipPregleda=" + tipPregleda +
                ", trajanjePregleda='" + trajanjePregleda + '\'' +
                ", sala=" + sala +
                ", lekar=" + lekar +
                ", cena=" + cena +
                ", izvestajOPregledu=" + izvestajOPregledu +
                '}';
    }
}
