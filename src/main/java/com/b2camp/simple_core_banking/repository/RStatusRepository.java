package com.b2camp.simple_core_banking.repository;

import com.b2camp.simple_core_banking.entity.RStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RStatusRepository extends JpaRepository<RStatus, String> {
}
