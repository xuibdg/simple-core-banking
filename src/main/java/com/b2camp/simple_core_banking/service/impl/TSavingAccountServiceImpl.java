package com.b2camp.simple_core_banking.service.impl;

import com.b2camp.simple_core_banking.dto.TSavingAccountRequest;
import com.b2camp.simple_core_banking.dto.TSavingAccountResponse;
import com.b2camp.simple_core_banking.entity.TSavingAccount;
import com.b2camp.simple_core_banking.repository.RStatusRepository;
import com.b2camp.simple_core_banking.repository.TSavingAccountRepository;
import com.b2camp.simple_core_banking.service.TSavingAccountService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;


@Repository
@Service
public class TSavingAccountServiceImpl implements TSavingAccountService {
    @Autowired
    private TSavingAccountRepository tSavingAccountRepository;

    @Autowired
    private RStatusRepository rStatusRepository;


    @Transactional
    @Override
    public TSavingAccountResponse create(TSavingAccountRequest tSavingAccountRequest) {
        TSavingAccount tSavingAccount = new TSavingAccount();
        buildToEntity(tSavingAccount, tSavingAccountRequest);
        tSavingAccountRepository.save(tSavingAccount);
        return buildToResponse(tSavingAccount);
    }

    private void buildToEntity(TSavingAccount tSavingAccount, TSavingAccountRequest request) {
        tSavingAccount.setAccountNumber(request.getAccountNumber());
        tSavingAccount.setSaving(request.getSavingId());
        tSavingAccount.setIsDeleted(false);
        tSavingAccount.setAuthorizationAt(Timestamp.from(Instant.now()).toLocalDateTime());

    }

    private TSavingAccountResponse buildToResponse(TSavingAccount tSavingAccount) {
        TSavingAccountResponse response = new TSavingAccountResponse();

        response.setSavingAccountId(tSavingAccount.getSavingAccountId());
        response.setAccountNumber(tSavingAccount.getAccountNumber());
        response.setBeginBalance(new BigDecimal("1000.00"));
        response.setEndBalance(new BigDecimal("1000.00"));
        response.setCurrentBalance(new BigDecimal("1000.00"));
        response.setSavingId(tSavingAccount.getSavingId());
        response.setAuthorizationAt(tSavingAccount.getAuthorizationAt());
        return response;
    }
}