package com.b2camp.simple_core_banking.service.impl;

import com.b2camp.simple_core_banking.dto.TSavingAccountResponse;
import com.b2camp.simple_core_banking.entity.RStatus;
import com.b2camp.simple_core_banking.entity.TSavingAccount;
import com.b2camp.simple_core_banking.enums.Status;
import com.b2camp.simple_core_banking.repository.RStatusRepository;
import com.b2camp.simple_core_banking.repository.TSavingAccountRepository;
import com.b2camp.simple_core_banking.service.TSavingAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;

@Slf4j
@Service
public class TSavingAccountServiceImpl implements TSavingAccountService {

    @Autowired
    private TSavingAccountRepository tSavingAccountRepository;

    @Autowired
    private RStatusRepository rStatusRepository;


    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String deleted(String savingAccountId) {

        TSavingAccount tSavingAccount = tSavingAccountRepository.findByIdAndIsDeletedFalse(savingAccountId)
                .orElseThrow(() -> new RuntimeException("Data Not Found"));
        if (tSavingAccount.getrStatus().getStatusId().equals(Status.ACTIVE.getKey())) {
            throw new RuntimeException("Status Active Cannot Deleted ");
        }
        tSavingAccount.setIsDeleted(true);
        tSavingAccountRepository.save(tSavingAccount);
        log.info("TSavingAccount deleted successfully for Account Number: {}, SavingAccountId: {}",
                tSavingAccount.getAccountNumber(),
                tSavingAccount.getSavingAccountId());
        return "Success delete by Saving Account Id : " + tSavingAccount.getSavingAccountId();
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public TSavingAccountResponse authorization(String savingAccountId) {
        TSavingAccount tSavingAccount = tSavingAccountRepository.findByIdAndIsDeletedFalse(savingAccountId)
                .orElseThrow(() -> new RuntimeException(" Data tSavingAccount Not Found"));

        validateStatus(tSavingAccount);
        RStatus rStatus = rStatusRepository.findById(Status.ACTIVE.getKey()).orElseThrow(() -> new RuntimeException("RStatus Not Found "));
        tSavingAccount.setrStatus(rStatus);
        tSavingAccount.setAuthorizationAt(Timestamp.from(Instant.now()));
        tSavingAccountRepository.save(tSavingAccount);
        log.info("Authorization completed for SavingAccountId: {}, Status: {}",
                savingAccountId,
                rStatus.getStatusId());
        return buildToResponseAccount(tSavingAccount);
    }

    public String validateStatus(TSavingAccount tSavingAccount) {
        if (tSavingAccount.getrStatus().getStatusId().equals(Status.ACTIVE.getKey())) {
            throw new RuntimeException("Data Already Active");
        }
        log.info("Validate savingAccountId success: {}", tSavingAccount.getSavingAccountId());
        return "Validate savingAccountId Success ";
    }

    private TSavingAccountResponse buildToResponseAccount(TSavingAccount tSavingAccount) {
        TSavingAccountResponse tSavingAccountResponse = new TSavingAccountResponse();
        tSavingAccountResponse.setSavingAccountId(tSavingAccount.getSavingAccountId());
        tSavingAccountResponse.setBeginBalance(tSavingAccount.getBeginBalance());
        tSavingAccountResponse.setAuthorizationAt(tSavingAccount.getAuthorizationAt());
        tSavingAccountResponse.setAccountNumber(tSavingAccount.getAccountNumber());
        tSavingAccountResponse.setEndBalance(tSavingAccount.getEndBalance());
        tSavingAccountResponse.setCurrentBalance(tSavingAccount.getCurrentBalance());
        tSavingAccountResponse.setSavingId(tSavingAccount.getmSaving().getSavingId());
        tSavingAccountResponse.setCifId(tSavingAccount.getmCifId().getCifId());
        tSavingAccountResponse.setSavingName(tSavingAccount.getmSaving().getSavingName());

        if (tSavingAccount.getrStatus() != null) {
            tSavingAccountResponse.setStatus(tSavingAccount.getrStatus().getStatusId());
            tSavingAccountResponse.setStatusName(tSavingAccount.getrStatus().getStatusName());
        }
        if (tSavingAccount.getmCifId() != null) {
            tSavingAccountResponse.setCifId(tSavingAccount.getmCifId().getCifId());
        }
        if (tSavingAccount.getrStatus() != null) {
            tSavingAccountResponse.setStatus(tSavingAccount.getrStatus().getStatusId());
        }

        return tSavingAccountResponse;
    }
}
