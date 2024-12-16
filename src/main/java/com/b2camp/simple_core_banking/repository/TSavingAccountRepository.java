package com.b2camp.simple_core_banking.repository;

import com.b2camp.simple_core_banking.entity.TSavingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TSavingAccountRepository extends JpaRepository<TSavingAccount, String> {

}
