package com.groupfour.MedicalCare.Model.Dokumenti;

import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Model.Osoblje.MedicinskaSestra;

public class Recept {
    private int id;
    private MedicinskaSestra medicinskaSestra;
    private Lekar lekar;
    private boolean overeno = false;
    private String idLeka;

    public Recept() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Recept(MedicinskaSestra medicinskaSestra, Lekar lekar, String idLeka) {
        this.medicinskaSestra = medicinskaSestra;
        this.lekar = lekar;
        this.idLeka = idLeka;
    }

    public String getIdLeka() {
        return idLeka;
    }

    public void setIdLeka(String idLeka) {
        this.idLeka = idLeka;
    }

    public MedicinskaSestra getMedicinskaSestra() {
        return medicinskaSestra;
    }

    public void setMedicinskaSestra(MedicinskaSestra medicinskaSestra) {
        this.medicinskaSestra = medicinskaSestra;
    }

    public Lekar getLekar() {
        return lekar;
    }

    public void setLekar(Lekar lekar) {
        this.lekar = lekar;
    }

    public boolean isOvereno() {
        return overeno;
    }

    public void setOvereno(boolean overeno) {
        this.overeno = overeno;
    }

    @Override
    public String toString() {
        return "Recept{" +
                "id=" + id +
                ", medicinskaSestra=" + medicinskaSestra +
                ", lekar=" + lekar +
                ", overeno=" + overeno +
                ", idLeka='" + idLeka + '\'' +
                '}';
    }
}
