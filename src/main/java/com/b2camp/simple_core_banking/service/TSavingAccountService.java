package com.b2camp.simple_core_banking.service;


import com.b2camp.simple_core_banking.dto.TSavingAccountResponse;
import com.b2camp.simple_core_banking.dto.TSavingAccountRequest;
import com.b2camp.simple_core_banking.dto.TSavingAccountResponse;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TSavingAccountService {
    TSavingAccountResponse updateSavingAccount(String savingAccountId, TSavingAccountRequest request);
    String deleted(String savingAccountId);

    TSavingAccountResponse authorization(String savingAccountId);
    TSavingAccountResponse create(TSavingAccountRequest tSavingAccountRequest);

    List<TSavingAccountResponse> readTSavingAccount(String accountNumber);

    Optional<TSavingAccountResponse> findBySavingAccountId(String savingAccountId);

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    TSavingAccountResponse updateSavingAccount(String savingAccountId, TSavingAccountRequest request);
}
