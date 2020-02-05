package com.groupfour.MedicalCare.Repository;

import com.groupfour.MedicalCare.Model.DTO.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Integer> {

}
