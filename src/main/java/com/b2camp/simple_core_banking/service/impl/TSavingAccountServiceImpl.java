package com.b2camp.simple_core_banking.service.impl;

import com.b2camp.simple_core_banking.dto.TSavingAccountResponse;
import com.b2camp.simple_core_banking.entity.TSavingAccount;
import com.b2camp.simple_core_banking.repository.TSavingAccountRepository;
import com.b2camp.simple_core_banking.service.TSavingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TSavingAccountServiceImpl implements TSavingAccountService {

    @Autowired
    TSavingAccountRepository tSavingAccountRepository;

    @Override
    public List<TSavingAccountResponse> readTsavingAccount(String accountNumber) {
        List<TSavingAccount>tSavingAccounts = tSavingAccountRepository.findAllByAccountNumberAndIsDeletedIsFalse(accountNumber);
        List<TSavingAccountResponse> responses = tSavingAccounts.stream().map(data -> buildRespons(data)).collect(Collectors.toUnmodifiableList());
        return responses;
    }

    @Override
    public Optional<TSavingAccountResponse> findBySavingAccountId(String savingAccountId) {
        TSavingAccount tSavingAccount = tSavingAccountRepository.findById(savingAccountId)
                .orElseThrow(()-> new RuntimeException("Data saving id tidak ada: " + savingAccountId));
        return Optional.of(buildRespons(tSavingAccount));
    }



    private TSavingAccountResponse buildRespons(TSavingAccount tSavingAccount) {
        TSavingAccountResponse tSavingAccountResponse = new TSavingAccountResponse();

        tSavingAccountResponse.setSavingAccountId(tSavingAccount.getSavingAccountId());

        tSavingAccountResponse.setAccountNumber(tSavingAccount.getAccountNumber());

        if (tSavingAccount.getmCifId() != null) {
            tSavingAccountResponse.setCifId(tSavingAccount.getmCifId().getCifId());
        } else {
            tSavingAccountResponse.setCifId(null);

        }

        tSavingAccountResponse.setBeginBalance(tSavingAccount.getBeginBalance());
        tSavingAccountResponse.setEndBalance(tSavingAccount.getEndBalance());
        tSavingAccountResponse.setCurrentBalance(tSavingAccount.getCurrentBalance());

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
