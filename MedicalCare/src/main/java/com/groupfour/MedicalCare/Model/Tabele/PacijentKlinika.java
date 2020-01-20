package com.groupfour.MedicalCare.Model.Tabele;

import com.groupfour.MedicalCare.Common.db.DbTableConstants;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = DbTableConstants.PACIJENT_KLINIKA)
@Table(name = DbTableConstants.PACIJENT_KLINIKA)
public class PacijentKlinika {
    @EmbeddedId()
    private Key key;

    @Embeddable
    public static class Key implements Serializable {
        @Column(name = "id_pacijent", nullable = false)
        private int id_pacijent;
        @Column(name = "id_klinike", nullable = false)
        private int id_klinike;

        protected Key(){}
        public Key (int id_p, int id_k){
            this.id_pacijent = id_p;
            this.id_klinike = id_k;
        }

        public int getId_pacijent(){
            return this.id_pacijent;
        }
        public int getId_klinike(){
            return this.id_klinike;
        }
    }
}


