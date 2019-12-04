package com.groupfour.MedicalCare.Model.Osoblje;

import com.groupfour.MedicalCare.Common.db.DbColumnConstants;
import com.groupfour.MedicalCare.Common.db.DbTableConstants;
import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;
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
    @Transient
    private Set<Pacijent> listaPacijenata = new HashSet<>();


}
