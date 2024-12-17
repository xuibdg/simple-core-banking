package com.b2camp.simple_core_banking.service.impl;

import com.b2camp.simple_core_banking.dto.TSavingAccountRequest;
import com.b2camp.simple_core_banking.dto.TSavingAccountResponse;
import com.b2camp.simple_core_banking.entity.RStatus;
import com.b2camp.simple_core_banking.entity.TSavingAccount;
import com.b2camp.simple_core_banking.repository.RStatusRepository;
import com.b2camp.simple_core_banking.repository.TSavingAccountRepository;
import com.b2camp.simple_core_banking.service.TSavingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TSavingAccountServiceImpl implements TSavingAccountService {

    @Autowired
    private TSavingAccountRepository tSavingAccountRepository;

    @Autowired
    private RStatusRepository rStatusRepository;


    @Override
    public String deleted(String savingAccountId) {
        TSavingAccount tSavingAccount = tSavingAccountRepository.findById(savingAccountId)
                .orElseThrow(() -> new RuntimeException("User Tidak Ada"));
        tSavingAccount.setIsDeleted(true);
        tSavingAccountRepository.save(tSavingAccount);

        return "Success delete by Saving Account Id : " + tSavingAccount.getSavingAccountId() + "\n" + "1. Uang Anda : Rp." + tSavingAccount.getCurrentBalance() ;

    }




    @Override
    public TSavingAccountResponse authorization(String savingAccountId) {

        TSavingAccount tSavingAccount = tSavingAccountRepository.findById(savingAccountId)
                .orElseThrow(() -> new RuntimeException("tSavingAccount Tidak Di Ketahui"));

        if (tSavingAccount.getrStatus().getStatusId().equals("2")) {
            throw new RuntimeException("Status Active");
        }
        RStatus rStatus = rStatusRepository.findById("2").orElseThrow(() -> new RuntimeException("Rstatus Not Found"));
        tSavingAccount.setrStatus(rStatus);
        tSavingAccountRepository.save(tSavingAccount);
        return buildToResponseAccount(tSavingAccount);
    }

    private TSavingAccountResponse buildToResponseAccount(TSavingAccount tSavingAccount) {
        TSavingAccountResponse tSavingAccountResponse = new TSavingAccountResponse();
        tSavingAccount.setAccountNumber(tSavingAccountResponse.getAccountNumber());

        if (tSavingAccount.getrStatus() != null) {
            tSavingAccountResponse.setSavingId(tSavingAccount.getrStatus().getStatusId());
        } else {
            tSavingAccountResponse.setStatusId(null);
        }


        return tSavingAccountResponse;
    }

    private TSavingAccount buildToEntity(TSavingAccount tSavingAccount, TSavingAccountRequest tSavingAccountRequest) {

        tSavingAccount.setAccountNumber(tSavingAccountRequest.getAccountNumber());
        tSavingAccount.setSavingId(tSavingAccountRequest.getSavingId());

        RStatus rStatus = rStatusRepository.findById(tSavingAccountRequest.getCifId()).orElseThrow(() ->
                new RuntimeException("User Role Not Found"));

        tSavingAccount.setSavingAccountId(rStatus.getStatusId());

        return tSavingAccount;
    }


}
