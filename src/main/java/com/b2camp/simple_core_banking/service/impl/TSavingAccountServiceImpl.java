package com.b2camp.simple_core_banking.service.impl;

import com.b2camp.simple_core_banking.dto.TSavingAccountResponse;
import com.b2camp.simple_core_banking.entity.MSaving;
import com.b2camp.simple_core_banking.entity.RStatus;
import com.b2camp.simple_core_banking.entity.TSavingAccount;
import com.b2camp.simple_core_banking.enums.Status;
import com.b2camp.simple_core_banking.repository.MCifRepository;
import com.b2camp.simple_core_banking.repository.MSavingRepository;
import com.b2camp.simple_core_banking.repository.RStatusRepository;
import com.b2camp.simple_core_banking.repository.TSavingAccountRepository;
import com.b2camp.simple_core_banking.dto.TSavingAccountRequest;
import com.b2camp.simple_core_banking.entity.MCif;
import com.b2camp.simple_core_banking.service.TSavingAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TSavingAccountServiceImpl implements TSavingAccountService {

    @Autowired
    private TSavingAccountRepository tSavingAccountRepository;

    @Autowired
    private RStatusRepository rStatusRepository;

    @Autowired
    private MCifRepository mCifRepository;

    @Autowired
    private MSavingRepository mSavingRepository;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String deleted(String savingAccountId) {

        TSavingAccount tSavingAccount = tSavingAccountRepository.findByIdAndIsDeletedFalse(savingAccountId)
                .orElseThrow(() -> new RuntimeException("Data Not Found"));
        if (tSavingAccount.getRStatus().getStatusId().equals(Status.ACTIVE.getKey())) {
            throw new RuntimeException("Status Active Cannot Deleted ");
        }
        tSavingAccount.setDeleted(true);
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
        tSavingAccount.setRStatus(rStatus);
        tSavingAccount.setAuthorizationAt(Timestamp.from(Instant.now()));
        tSavingAccountRepository.save(tSavingAccount);
        log.info("Authorization completed for SavingAccountId: {}, Status: {}",
                savingAccountId,
                rStatus.getStatusId());
        return buildToResponseAccount(tSavingAccount);
    }

    public String validateStatus(TSavingAccount tSavingAccount) {
        if (tSavingAccount.getRStatus().getStatusId().equals(Status.ACTIVE.getKey())) {
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
        tSavingAccountResponse.setCifId(tSavingAccount.getMCifId().getCifId());
        tSavingAccountResponse.setSavingName(tSavingAccount.getMSaving().getSavingName());

        if (tSavingAccount.getUpdatedAt() != null) {
            tSavingAccountResponse.setUpdatedAt(tSavingAccount.getUpdatedAt());
        }

        if (tSavingAccount.getUpdatedBy() != null) {
            tSavingAccountResponse.setUpdatedBy(tSavingAccount.getUpdatedBy().getUserName());
        }

        if (tSavingAccount.getCreatedAt() != null) {
            tSavingAccountResponse.setCreatedAt(tSavingAccount.getCreatedAt());
        }

        if (tSavingAccount.getCreatedBy() != null) {
            tSavingAccountResponse.setCreatedBy(tSavingAccount.getCreatedBy().getUserName());
        }

        if (tSavingAccount.getRStatus() != null) {
            tSavingAccountResponse.setStatusId(tSavingAccount.getRStatus().getStatusId());
            tSavingAccountResponse.setStatusName(tSavingAccount.getRStatus().getStatusName());
        }
        if (tSavingAccount.getMCifId() != null) {
            tSavingAccountResponse.setCifId(tSavingAccount.getMCifId().getCifId());
        }
        if (tSavingAccount.getMSaving() != null) {
            tSavingAccountResponse.setSavingId(tSavingAccount.getMSaving().getSavingId());
        }

        return tSavingAccountResponse;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public TSavingAccountRequest create(TSavingAccountRequest tSavingAccountRequest) {
        TSavingAccount tSavingAccount = new TSavingAccount();
        buildToEntityForCreate(tSavingAccount, tSavingAccountRequest);

        return tSavingAccountRequest;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public TSavingAccountResponse updateSavingAccount(String savingAccountId, TSavingAccountRequest request) {
        TSavingAccount tSavingAccount = tSavingAccountRepository.findByIdAndIsDeletedFalse(savingAccountId).orElse(null);

        log.error("TSavingAccountServiceImpl UpdateSavingAccount: {}", tSavingAccount);
            buildToEntityForCreate(tSavingAccount,request);

        log.info("TSavingAccountServiceImpl UpdateSavingAccount Save");
        tSavingAccountRepository.save(tSavingAccount);
        tSavingAccount.setUpdatedAt(Timestamp.from(Instant.now()));

        log.info("TSavingAccountServiceImpl create, success save to tSavingAccount : {}", tSavingAccount);
        return buildToResponseAccount(tSavingAccount);

    }


    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<TSavingAccountResponse> readTSavingAccount(String accountNumber) {
        List<TSavingAccount> tSavingAccounts = tSavingAccountRepository.findAllByAccountNumberAndIsDeletedIsFalse(accountNumber);
        List<TSavingAccountResponse> responses = tSavingAccounts.stream().map(data -> buildToResponseAccount(data)).collect(Collectors.toList());
        return responses;
    }


    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Optional<TSavingAccountResponse> findBySavingAccountId(String savingAccountId) {
        TSavingAccount tSavingAccount = tSavingAccountRepository.findById(savingAccountId)
                .orElseThrow(() -> new RuntimeException("Data saving id tidak ada: " + savingAccountId));
        log.info("Data Terlihat. {}", tSavingAccount.getSavingAccountId());
        return Optional.of(buildToResponseAccount(tSavingAccount));

    }

    private void buildToEntityForCreate(TSavingAccount tSavingAccount, TSavingAccountRequest request) {
        log.info("TSavingAccountServiceImpl buildToEntity, process build to entity TSavingAccount : {}", tSavingAccount.getAccountNumber());
        tSavingAccount.setAccountNumber(request.getAccountNumber());
        tSavingAccount.setBeginBalance(request.getBeginBalance());
        tSavingAccount.setEndBalance(request.getEndBalance());
        tSavingAccount.setCurrentBalance(request.getCurrentBalance());
        tSavingAccount.setDeleted(false);
//        tSavingAccount.setCre(Timestamp.from(Instant.now()));


        log.info("TSavingAccountServiceImpl buildToEntity, process search data rStatusId from : {}", tSavingAccount);
        RStatus rStatus = rStatusRepository.findById(Status.PENDING.getKey())
                .orElseThrow(() -> new RuntimeException("Not Found"));
        tSavingAccount.setRStatus(rStatus);

        log.info("TSavingAccountServiceImpl buildToEntity, process search data mCif from : {} ", tSavingAccount);
        MCif mCif = mCifRepository.findById(request.getCifId())
                .orElseThrow(() -> new RuntimeException("cifId not found"));
        tSavingAccount.setMCifId(mCif);

        log.info("TSavingAccountServiceImpl buildToEntity, process search data mSaving from : {} ", tSavingAccount);
        MSaving mSaving = mSavingRepository.findById(request.getSavingId())
                .orElseThrow(() -> new RuntimeException("savingId not found"));
        tSavingAccount.setMSaving(mSaving);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    private TSavingAccountRequest buildEntity(TSavingAccount tSavingAccount, TSavingAccountRequest request) {
        log.info("TSavingAccountServiceImpl buildEntity Process Update");
        tSavingAccount.setAccountNumber(request.getAccountNumber());

        MCif mCif = mCifRepository.findById(request.getCifId())
                .orElseThrow(() -> new RuntimeException("MCif not found"));
        tSavingAccount.setMCifId(mCif);

        MSaving mSaving = mSavingRepository.findById(request.getSavingId())
                .orElseThrow(() -> new RuntimeException("MSaving not Found"));
        tSavingAccount.setMSaving(mSaving);
        log.info("TSavingAccountServiceImpl buildEntity : {}", tSavingAccount);

        return request;
    }
}

