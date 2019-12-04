package com.groupfour.MedicalCare.Model.Klinika;

import com.groupfour.MedicalCare.Common.db.DbColumnConstants;
import com.groupfour.MedicalCare.Common.db.DbTableConstants;
import com.groupfour.MedicalCare.Model.Zahtevi.Operacija;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = DbTableConstants.SALA)
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DbColumnConstants.SALA_ID)
    private int id;
    @Column(name = DbColumnConstants.SALA_ZAUZECE)
    private boolean zauzeta = false;
    @Column(name = DbColumnConstants.SALA_POCETAK_TERMINA)
    private LocalDateTime pocetakTermina;
    @Column(name = DbColumnConstants.SALA_ZAVRSETAK_TERMINA)
    private LocalDateTime krajTermina;
    @Column(name = DbColumnConstants.SALA_BROJ_SALE)
    private int brojSale;

    @Transient
    private Operacija operacija;

}
