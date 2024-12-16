package com.b2camp.simple_core_banking.repository;

import com.b2camp.simple_core_banking.entity.MUser;
import com.b2camp.simple_core_banking.entity.TSavingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TSavingAccountRepository extends JpaRepository<TSavingAccount, String> {

    @Query("SELECT tsa FROM TSavingAccount tsa WHERE (:accountNumber IS NULL OR :accountNumber = '' OR LOWER(tsa.accountNumber) LIKE LOWER(CONCAT('%', :accountNumber, '%'))) AND tsa.isDeleted = false")
    List<TSavingAccount> findAllByAccountNumberAndIsDeletedIsFalse(@Param("accountNumber") String accountNumber);

    Optional<TSavingAccount>findBySavingId(String sevingId);


}
