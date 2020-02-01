package com.groupfour.MedicalCare.Model.Pregled;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.groupfour.MedicalCare.Common.db.DbColumnConstants;
import com.groupfour.MedicalCare.Common.db.DbTableConstants;
import com.groupfour.MedicalCare.Model.Klinika.Sala;
import com.groupfour.MedicalCare.Model.Osoblje.Lekar;
import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = DbTableConstants.OPERACIJA)
@JsonIgnoreProperties({"sala", "pacijent"})
public class Operacija {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DbColumnConstants.OPERACIJA_ID)
    private int id;
    @Column(name = DbColumnConstants.OPERACIJA_TERMIN)
    private LocalDateTime terminOperacije;
    @Column(name = DbColumnConstants.TRAJANJE_OPERACIJE)
    private int trajanjeOperacije;
    @Column(name = DbColumnConstants.OPERACIJA_AKTIVNA)
    private boolean aktivan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DbColumnConstants.OPERACIJA_SALA)
    private Sala sala;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DbColumnConstants.OPERACIJA_PACIJENT)
    private Pacijent pacijent;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = DbTableConstants.LEKAR_OPERACIJA,
            joinColumns = @JoinColumn(name = DbColumnConstants.OPERACIJA_ID),
            inverseJoinColumns = @JoinColumn(name = DbColumnConstants.LEKAR_ID)
    )
    private Set<Lekar> lekar = new HashSet<>();
}
