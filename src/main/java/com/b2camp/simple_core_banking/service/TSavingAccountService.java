package com.b2camp.simple_core_banking.service;

import com.b2camp.simple_core_banking.dto.TSavingAccountResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TSavingAccountService {
    List<TSavingAccountResponse>readTsavingAccount(String accountNumber);

    Optional<TSavingAccountResponse>findBySavingAccountId(String savingAccountId);
}
