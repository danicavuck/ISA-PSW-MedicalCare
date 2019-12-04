package com.groupfour.MedicalCare.Model.Zahtevi;
import java.time.LocalDateTime;

public class Odsustvo {
    private int id;
    private LocalDateTime pocetakOdsustva;
    private LocalDateTime krajOdsustva;
    private boolean odobren;

    public Odsustvo(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getPocetakOdsustva() {
        return pocetakOdsustva;
    }

    public void setPocetakOdsustva(LocalDateTime pocetakOdsustva) {
        this.pocetakOdsustva = pocetakOdsustva;
    }

    public LocalDateTime getKrajOdsustva() {
        return krajOdsustva;
    }

    public void setKrajOdsustva(LocalDateTime krajOdsustva) {
        this.krajOdsustva = krajOdsustva;
    }

    public boolean isOdobren() {
        return odobren;
    }

    public void setOdobren(boolean odobren) {
        this.odobren = odobren;
    }

    @Override
    public String toString() {
        return "Odsustvo{" +
                "id=" + id +
                ", pocetakOdsustva=" + pocetakOdsustva +
                ", krajOdsustva=" + krajOdsustva +
                ", odobren=" + odobren +
                '}';
    }
}
