package com.groupfour.MedicalCare.Model.Pregled;
import java.util.HashSet;
import java.util.Set;

public class TipPregleda {
    private int id;
    private Set<String> tipoviPregleda = new HashSet<>();

    public TipPregleda() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<String> getTipoviPregleda() {
        return tipoviPregleda;
    }

    public void setTipoviPregleda(Set<String> tipoviPregleda) {
        this.tipoviPregleda = tipoviPregleda;
    }

    @Override
    public String toString() {
        return "TipPregleda{" +
                "id=" + id +
                ", tipoviPregleda=" + tipoviPregleda +
                '}';
    }


}
