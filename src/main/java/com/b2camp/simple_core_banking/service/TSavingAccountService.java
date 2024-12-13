package com.b2camp.simple_core_banking.service;

import com.b2camp.simple_core_banking.dto.TSavingAccountRequest;
import com.b2camp.simple_core_banking.dto.TSavingAccountResponse;

public interface TSavingAccountService {
    TSavingAccountResponse updateSavingAccount(String savingAccountId, TSavingAccountRequest request);
}
