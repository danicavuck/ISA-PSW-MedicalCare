package com.groupfour.MedicalCare.Model.Pregled;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.groupfour.MedicalCare.Common.db.DbColumnConstants;
import com.groupfour.MedicalCare.Common.db.DbTableConstants;
import com.groupfour.MedicalCare.Model.Dokumenti.IzvestajOPregledu;
import com.groupfour.MedicalCare.Model.Klinika.Sala;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = DbTableConstants.PREGLEDI_CEKANJE)
@JsonIgnoreProperties({"izvestajOPregledu", "pacijent", "lekar", "sala"})
public class PreglediNaCekanju {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DbColumnConstants.PREGLED_ID)
    private int id;
    @Column(name = DbColumnConstants.PREGLED_TERMIN)
    private LocalDateTime terminPregleda;
    @Column(name = DbColumnConstants.PREGLED_TRAJANJE)
    private int trajanjePregleda;
    @Column(name = DbColumnConstants.PREGLED_CENA)
    private int cena;
    @Column(name = DbColumnConstants.PREGLED_POPUST)
    private int popust;
    @Column(name = DbColumnConstants.PREGLED_AKTIVAN)
    private boolean aktivan;
    @Column(name = DbColumnConstants.PREGLED_KLINIKA)
    private int klinikaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DbColumnConstants.PREGLED_SALA)
    @JsonIgnoreProperties("pregledi")
    private Sala sala;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DbColumnConstants.PREGLED_TIP_PREGLEDA)
    private TipPregleda tipPregleda;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DbColumnConstants.PREGLED_LEKAR)
    private Lekar lekar;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = DbColumnConstants.PREGLED_IZVESTAJ)
    private IzvestajOPregledu izvestajOPregledu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DbColumnConstants.PREGLED_PACIJENT)
    @JsonIgnoreProperties("listaPregleda")
    private Pacijent pacijent;

    @Override
    public String toString() {
        return "Pregled na cekanju id: " + this.id + " Tip pregleda: " + this.tipPregleda.getTipPregleda();
    }

}
