package com.groupfour.MedicalCare.Model.Zahtevi;

import com.groupfour.MedicalCare.Model.Administrator.AdminKlinickogCentra;
import com.groupfour.MedicalCare.Common.db.DbColumnConstants;
import com.groupfour.MedicalCare.Common.db.DbTableConstants;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = DbTableConstants.REGISTRACIJAPACIJENATA)
public class RegistracijaPacijenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DbColumnConstants.REGISTRACIJA_ID)
    private int id;
    @Column(name = DbColumnConstants.REGISTRACIJA_EMAIL)
    private String email;
    @Column(name = DbColumnConstants.REGISTRACIJA_LOZINKA)
    private String lozinka;
    @Column(name = DbColumnConstants.REGISTRACIJA_IME)
    private String ime;
    @Column(name = DbColumnConstants.REGISTRACIJA_PREZIME)
    private String prezime;
    @Column(name = DbColumnConstants.REGISTRACIJA_ADRESA)
    private String adresa;
    @Column(name = DbColumnConstants.REGISTRACIJA_GRAD)
    private String grad;
    @Column(name = DbColumnConstants.REGISTRACIJA_DRZAVA)
    private String drzava;
    @Column(name = DbColumnConstants.REGISTRACIJA_TELEFON)
    private String brojTelefona;
    @Column(name = DbColumnConstants.REGISTRACIJA_OSIGURANJE)
    private String brojOsiguranja;
    @Column(name = DbColumnConstants.REGISTRACIJA_ODOBREN)
    private boolean odobren = false;
    @Column(name = DbColumnConstants.REGISTRACIJA_RAZLOG_ODBIJANJA)
    private String razlogOdbijanja;

    @Column(name = DbColumnConstants.REGISTRACIJA_AKTIVAN)
    private boolean aktivan = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DbColumnConstants.REGISTRACIJA_ADMIN_KC)
    private AdminKlinickogCentra adminKlinickogCentra;



    public void setAdminKlinickogCentra(AdminKlinickogCentra adminKlinickogCentra) {
        this.adminKlinickogCentra = adminKlinickogCentra;
    }

}
