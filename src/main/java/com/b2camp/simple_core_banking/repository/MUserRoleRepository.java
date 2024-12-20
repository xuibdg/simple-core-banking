package com.b2camp.simple_core_banking.repository;

import com.b2camp.simple_core_banking.entity.MUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MUserRoleRepository extends JpaRepository<MUserRole, String> {
}
