package com.b2camp.simple_core_banking.service;


import com.b2camp.simple_core_banking.dto.TSavingAccountResponse;
import org.springframework.stereotype.Service;
import com.b2camp.simple_core_banking.dto.TSavingAccountRequest;

import java.util.List;
import java.util.Optional;

public interface TSavingAccountService {
    String deleted(String savingAccountId);

    TSavingAccountResponse authorization(String savingAccountId);
    TSavingAccountResponse create(TSavingAccountRequest tSavingAccountRequest);

    List<TSavingAccountResponse> readTSavingAccount(String accountNumber);

    Optional<TSavingAccountResponse> findBySavingAccountId(String savingAccountId);
}
