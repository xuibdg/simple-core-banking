package com.b2camp.simple_core_banking.repository;

import com.b2camp.simple_core_banking.entity.TSavingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface TSavingAccountRepository extends JpaRepository<TSavingAccount, String> {

    @Query(value = "select * from t_saving_account tsa where is_deleted = false and saving_account_id = :savingAccountId", nativeQuery = true)
    Optional<TSavingAccount> findByIdAndIsDeletedFalse(@Param("savingAccountId") String savingAccountId);

    @Query(value = "select * from t_saving_account tsa where  account_number like concat('%',:accountNumber ,'%')  \n" +
            "and is_deleted is false ",nativeQuery = true)
    List<TSavingAccount> findAllByAccountNumberAndIsDeletedIsFalse(@Param("accountNumber") String accountNumber);
}
