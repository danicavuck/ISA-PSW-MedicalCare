package com.groupfour.MedicalCare.Model.DTO;

import com.groupfour.MedicalCare.Common.db.DbColumnConstants;
import com.groupfour.MedicalCare.Common.db.DbTableConstants;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = DbTableConstants.USER_ROLE)
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DbColumnConstants.ROLE_ID)
    private int id;
    @Column(name = DbColumnConstants.ROLE_USER)
    private String user_email;
    @Column(name = DbColumnConstants.ROLE_DESCRIPTION)
    private String role;
}
