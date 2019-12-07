package com.groupfour.MedicalCare.Model.Administrator;

import com.groupfour.MedicalCare.Common.db.DbColumnConstants;
import com.groupfour.MedicalCare.Common.db.DbTableConstants;
import com.groupfour.MedicalCare.Model.Klinika.Klinika;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = DbTableConstants.ADMIN_KLINIKE)
public class AdminKlinike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DbColumnConstants.ADMIN_KLINIKE_ID)
    private int id;
    @Column(name = DbColumnConstants.ADMIN_KLINIKE_MEJL)
    private String email;
    @Column(name = DbColumnConstants.ADMIN_KLINIKE_LOZINKA)
    private String lozinka;
    @Column(name = DbColumnConstants.ADMIN_KLINIKE_IME)
    private String ime;
    @Column(name = DbColumnConstants.ADMIN_KLINIKE_PREZIME)
    private String prezime;
    @Column(name = DbColumnConstants.ADMIN_KLINIKE_AKTIVAN)
    private boolean aktivan = true;
    @Column(name = DbColumnConstants.ADMIN_KLINIKE_PRVO_LOGOVANJE)
    private boolean prviPutLogovan = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DbColumnConstants.ADMIN_KLINIKE_KLINIKA)
    private Klinika klinika;

    public void dodajKliniku(Klinika k){
        this.klinika = k;
        k.dodajAdminaKlinike(this);
    }

}
