package com.b2camp.simple_core_banking.service.impl;

import com.b2camp.simple_core_banking.dto.TSavingAccountResponse;
import com.b2camp.simple_core_banking.entity.TSavingAccount;
import com.b2camp.simple_core_banking.repository.TSavingAccountRepository;
import com.b2camp.simple_core_banking.service.TSavingAccountService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TSavingAccountServiceImpl implements TSavingAccountService {

    private static final Logger log = LoggerFactory.getLogger(TSavingAccountServiceImpl.class);
    @Autowired
    TSavingAccountRepository tSavingAccountRepository;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<TSavingAccountResponse> readTSavingAccount(String accountNumber) {
        List<TSavingAccount> tSavingAccounts = tSavingAccountRepository.findAllByAccountNumberAndIsDeletedIsFalse(accountNumber);
        List<TSavingAccountResponse> responses = tSavingAccounts.stream().map(data -> buildResponse(data)).collect(Collectors.toUnmodifiableList());
        return responses;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
            public Optional<TSavingAccountResponse> findBySavingAccountId(String savingAccountId) {
        TSavingAccount tSavingAccount = tSavingAccountRepository.findById(savingAccountId)
                .orElseThrow(() -> new RuntimeException("Data saving id tidak ada: " + savingAccountId));
        log.info("Data Terlihat. {}", tSavingAccount.getSavingAccountId());
        return Optional.of(buildResponse(tSavingAccount));

    }


    private TSavingAccountResponse buildResponse(TSavingAccount tSavingAccount) {
        TSavingAccountResponse tSavingAccountResponse = new TSavingAccountResponse();
        tSavingAccountResponse.setSavingAccountId(tSavingAccount.getSavingAccountId());
        tSavingAccountResponse.setAccountNumber(tSavingAccount.getAccountNumber());
        tSavingAccountResponse.setBeginBalance(tSavingAccount.getBeginBalance());
        tSavingAccountResponse.setEndBalance(tSavingAccount.getEndBalance());
        tSavingAccountResponse.setCurrentBalance(tSavingAccount.getCurrentBalance());

        if (tSavingAccount.getmCifId() != null) {
            tSavingAccountResponse.setCifId(tSavingAccount.getmCifId().getCifId());
        } else {
            tSavingAccountResponse.setCifId(null);

        }


        if (tSavingAccount.getrStatus() != null) {
            tSavingAccountResponse.setStatusId(tSavingAccount.getrStatus().getStatusId());
        } else {
            tSavingAccountResponse.setStatusId(null);
        }

        tSavingAccountResponse.setDeleted(tSavingAccount.isDeleted());


        if (tSavingAccount.getSavingAccountId() != null) {
            tSavingAccountResponse.setSavingAccountId(tSavingAccount.getSavingAccountId());
        } else {
            tSavingAccountResponse.setSavingAccountId(null);
        }

        return tSavingAccountResponse;
    }

}
