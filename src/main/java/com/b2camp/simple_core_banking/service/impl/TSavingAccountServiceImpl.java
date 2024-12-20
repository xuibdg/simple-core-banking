package com.b2camp.simple_core_banking.service.impl;

import com.b2camp.simple_core_banking.dto.TSavingAccountResponse;
import com.b2camp.simple_core_banking.entity.RStatus;
import com.b2camp.simple_core_banking.entity.TSavingAccount;
import com.b2camp.simple_core_banking.enums.Status;
import com.b2camp.simple_core_banking.repository.MCifRepository;
import com.b2camp.simple_core_banking.repository.RStatusRepository;
import com.b2camp.simple_core_banking.repository.TSavingAccountRepository;
import com.b2camp.simple_core_banking.dto.TSavingAccountRequest;
import com.b2camp.simple_core_banking.dto.TSavingAccountResponse;
import com.b2camp.simple_core_banking.entity.MCif;
import com.b2camp.simple_core_banking.service.TSavingAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

@Repository
@Service
@Slf4j
public class TSavingAccountServiceImpl implements TSavingAccountService {

    @Autowired
    private TSavingAccountRepository tSavingAccountRepository;

    @Autowired
    private RStatusRepository rStatusRepository;

    @Autowired
    private MCifRepository mCifRepository;

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

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public TSavingAccountResponse create(TSavingAccountRequest tSavingAccountRequest) {
        TSavingAccount tSavingAccount = new TSavingAccount();
        buildToEntity(tSavingAccount, tSavingAccountRequest);
        tSavingAccountRepository.save(tSavingAccount);
        log.info("TSavingAccountServiceImpl create, succes save to tSavingAccount : {}", tSavingAccount);
        return buildToResponse(tSavingAccount);

    }

    private void buildToEntity(TSavingAccount tSavingAccount, TSavingAccountRequest request) {
        log.info("TSavingAccountServiceImpl buildToEntity, process build to entity TSavingAccount : {}", tSavingAccount.getAccountNumber());
        tSavingAccount.setAccountNumber(request.getAccountNumber());
        tSavingAccount.setSaving(request.getSavingId());
        tSavingAccount.setBeginBalance(new BigDecimal("6000.00"));
        tSavingAccount.setEndBalance(new BigDecimal("6000.00"));
        tSavingAccount.setCurrentBalance(new BigDecimal("6000.00"));
        tSavingAccount.setIsDeleted(false);
        tSavingAccount.setAuthorizationAt(Timestamp.from(Instant.now()).toLocalDateTime());


        log.info("TSavingAccountServiceImpl rStatus, process search data rStatusId from : {}", tSavingAccount);
        RStatus rStatus = rStatusRepository.findById("1")
                .orElseThrow(() -> new RuntimeException("Not Found"));
        tSavingAccount.setrStatus(rStatus);

        log.info("TSavingAccountServiceImpl CifId, process search data cifId TSavingAccount from : {} ", tSavingAccount);
        MCif mCif = mCifRepository.findById("12133")
                .orElseThrow(() -> new RuntimeException("CifId not found"));
        tSavingAccount.setCifId(mCif);

    }

    private TSavingAccountResponse buildToResponse(TSavingAccount tSavingAccount) {
        TSavingAccountResponse response = new TSavingAccountResponse();

        response.setSavingAccountId(tSavingAccount.getSavingAccountId());
        response.setAccountNumber(tSavingAccount.getAccountNumber());
        response.setBeginBalance(new BigDecimal("6000.00"));
        response.setEndBalance(new BigDecimal("6000.00"));
        response.setCurrentBalance(new BigDecimal("6000.00"));
        response.setSavingId(tSavingAccount.getSavingId());
        response.setAuthorizationAt(tSavingAccount.getAuthorizationAt());

        if (tSavingAccount.getCifId() != null) {
            response.setCifId(tSavingAccount.getCifId().getCifId());
        } else {
            log.info("TSavingAccountServiceImpl buildToResponse,Failed getting CifId : {}", tSavingAccount);
            response.setCifId(null);
        }
        return response;
    }
}