package com.b2camp.simple_core_banking.service;


import com.b2camp.simple_core_banking.dto.TSavingAccountResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TSavingAccountService {
    String deleted(String savingAccountId);

    TSavingAccountResponse authorization(String savingAccountId);

    List<TSavingAccountResponse> readTSavingAccount(String accountNumber);

    Optional<TSavingAccountResponse> findBySavingAccountId(String savingAccountId);
}
