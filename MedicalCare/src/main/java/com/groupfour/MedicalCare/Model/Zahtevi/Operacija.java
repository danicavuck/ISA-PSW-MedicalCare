package com.groupfour.MedicalCare.Model.Zahtevi;

import com.groupfour.MedicalCare.Model.Klinika.Sala;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;

import java.time.LocalDateTime;

public class Operacija {
    private int id;
    private LocalDateTime terminOperacije;
    private Sala sala;
    private Lekar lekar;
    private Pacijent pacijent;

    public Operacija(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getTerminOperacije() {
        return terminOperacije;
    }

    public void setTerminOperacije(LocalDateTime terminOperacije) {
        this.terminOperacije = terminOperacije;
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

    public Pacijent getPacijent() {
        return pacijent;
    }

    public void setPacijent(Pacijent pacijent) {
        this.pacijent = pacijent;
    }

    @Override
    public String toString() {
        return "Operacija{" +
                "id=" + id +
                ", terminOperacije=" + terminOperacije +
                ", sala=" + sala +
                ", lekar=" + lekar +
                ", pacijent=" + pacijent +
                '}';
    }
}
