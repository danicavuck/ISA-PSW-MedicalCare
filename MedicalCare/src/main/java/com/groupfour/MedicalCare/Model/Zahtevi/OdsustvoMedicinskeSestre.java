package com.groupfour.MedicalCare.Model.Zahtevi;

import com.groupfour.MedicalCare.Common.db.DbColumnConstants;
import com.groupfour.MedicalCare.Common.db.DbTableConstants;
import com.groupfour.MedicalCare.Model.Osoblje.MedicinskaSestra;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = DbTableConstants.ODSUSTVO_MEDICINSKE_SESTRE)
public class OdsustvoMedicinskeSestre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DbColumnConstants.ODSUSTVO_ID)
    private int id;
    @Column(name = DbColumnConstants.ODSUSTVO_POCETAK)
    private LocalDateTime pocetakOdsustva;
    @Column(name = DbColumnConstants.ODSUSTVO_KRAJ)
    private LocalDateTime krajOdsustva;
    @Column(name = DbColumnConstants.ODSUSTVO_ODOBRRENO)
    private boolean odobren;
    @Column(name = DbColumnConstants.ODSUSTVO_AKTIVNO)
    private boolean aktivno = true;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DbColumnConstants.ODSUSTVO_MEDICINSKA_SESTRA)
    private MedicinskaSestra medicinskaSestra;

}
