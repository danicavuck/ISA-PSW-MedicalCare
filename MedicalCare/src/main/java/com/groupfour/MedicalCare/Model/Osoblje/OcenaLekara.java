package com.groupfour.MedicalCare.Model.Osoblje;

import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;

public class OcenaLekara {
    private int id;
    private Lekar lekar;
    private Pacijent pacijent;
    private int ocena;

    public OcenaLekara() {
    }

    public OcenaLekara(Lekar lekar, Pacijent pacijent, int ocena) {
        this.lekar = lekar;
        this.pacijent = pacijent;
        this.ocena = ocena;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }

    @Override
    public String toString() {
        return "OcenaLekara{" +
                "id=" + id +
                ", lekar=" + lekar +
                ", pacijent=" + pacijent +
                ", ocena=" + ocena +
                '}';
    }
}
