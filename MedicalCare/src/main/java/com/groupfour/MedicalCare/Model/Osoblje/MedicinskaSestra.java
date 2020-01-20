package com.groupfour.MedicalCare.Model.Osoblje;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.groupfour.MedicalCare.Common.db.DbColumnConstants;
import com.groupfour.MedicalCare.Common.db.DbTableConstants;
import com.groupfour.MedicalCare.Model.Dokumenti.Recept;
import com.groupfour.MedicalCare.Model.Klinika.Klinika;
import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;
import com.groupfour.MedicalCare.Model.Zahtevi.OdsustvoMedicinskeSestre;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = DbTableConstants.MEDICINSKA_SESTRA)
public class MedicinskaSestra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DbColumnConstants.MEDICINSKA_SESTRA_ID)
    private int id;
    @Column(name = DbColumnConstants.MEDICINSKA_SESTRA_EMAIL)
    private String email;
    @Column(name = DbColumnConstants.MEDICINSKA_SESTRA_IME)
    private String ime;
    @Column(name = DbColumnConstants.MEDICINSKA_SESTRA_PREZIME)
    private String prezime;
    @Column(name = DbColumnConstants.MEDICINSKA_SESTRA_LOZINKA)
    private String lozinka;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = DbColumnConstants.MEDICINSKA_SESTRA_KLINIKA)
    private Klinika klinika;

    @ManyToMany
    @JoinTable(
            name = DbTableConstants.MED_SESTRA_PACIJENT,
            joinColumns = @JoinColumn(name = DbColumnConstants.MEDICINSKA_SESTRA_SESTRA),
            inverseJoinColumns = @JoinColumn(name = DbColumnConstants.MEDICINSKA_SESTRA_PACIJENT)
    )
    @JsonIgnoreProperties("listaSestara")
    private Set<Pacijent> listaPacijenata = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "medicinskaSestra")
    private Set<OdsustvoMedicinskeSestre> listaOdsustva = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "medicinskaSestra", cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<Recept> recepti = new HashSet<>();


    public void dodajPacijenta(Pacijent pacijent) {
        this.listaPacijenata.add(pacijent);
        pacijent.getListaSestara().add(this);
    }

    public void dodajRecept(Recept recept) {
        this.recepti.add(recept);
        recept.setMedicinskaSestra(this);
    }


}
