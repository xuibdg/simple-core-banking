package com.b2camp.simple_core_banking.service.impl;

import com.b2camp.simple_core_banking.dto.TSavingAccountResponse;
import com.b2camp.simple_core_banking.entity.RStatus;
import com.b2camp.simple_core_banking.entity.TSavingAccount;
import com.b2camp.simple_core_banking.repository.RStatusRepository;
import com.b2camp.simple_core_banking.repository.TSavingAccountRepository;
import com.b2camp.simple_core_banking.service.TSavingAccountService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
        try {
            TSavingAccount tSavingAccount = tSavingAccountRepository.findById(savingAccountId)
                    .orElseThrow(() -> new RuntimeException("User Tidak Ada"));
            validateDeleted(tSavingAccount);
            tSavingAccount.setIsDeleted(true);
            log.info("TSavingAccount deleted successfully for Account Number: {}, SavingAccountId: {}",
                    tSavingAccount.getAccountNumber(),
                    tSavingAccount.getSavingAccountId());
            tSavingAccountRepository.save(tSavingAccount);
            return "Success delete by Saving Account Id : " + tSavingAccount.getSavingAccountId();


        } catch (RuntimeException a) {
            log.error("Error deleting TSavingAccount. SavingAccountId: {}, Error: {}", savingAccountId, a.getMessage());
            throw a;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void validateDeleted(TSavingAccount tSavingAccount) {
        tSavingAccount.isDeleted();
        if (tSavingAccount.isDeleted() == true) {
            throw new EntityNotFoundException("tSavingAccount isDeleted id has been deleted : " + tSavingAccount.getSavingAccountId());
        }

    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public TSavingAccountResponse authorization(String savingAccountId) {

        TSavingAccount tSavingAccount = tSavingAccountRepository.findById(savingAccountId)
                .orElseThrow(() -> new RuntimeException("tSavingAccount Tidak Di Ketahui"));

        validasiStatus(savingAccountId);
        RStatus rStatus = rStatusRepository.findById("4").orElseThrow(() -> new RuntimeException("Rstatus Tidak Di ketahui"));
        tSavingAccount.setrStatus(rStatus);
        tSavingAccountRepository.save(tSavingAccount);
        log.info("Authorization completed for SavingAccountId: {}, Status: {}",
                savingAccountId,
                rStatus.getStatusId());
        return buildToResponseAccount(tSavingAccount);
    }


    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public String validasiStatus(String savingAccountId) {
        TSavingAccount tSavingAccount = tSavingAccountRepository.findById(savingAccountId)
                .orElseThrow(() -> new RuntimeException("TSavingAccount Not Found"));
        if (tSavingAccount.getrStatus().getStatusId().equals("4")) {
            throw new RuntimeException("Status Active");
        }
        log.info("Validasi savingAccountId success: {}", savingAccountId);


        return "Validasi savingAccountId Success ";
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    private TSavingAccountResponse buildToResponseAccount(TSavingAccount tSavingAccount) {
        TSavingAccountResponse tSavingAccountResponse = new TSavingAccountResponse();
        tSavingAccountResponse.setSavingAccountId(tSavingAccount.getSavingAccountId());
        tSavingAccountResponse.setBeginBalance(tSavingAccount.getBeginBalance());
        tSavingAccountResponse.setAuthorizationAt(tSavingAccount.getAuthorizationAt().toLocalDateTime());
        tSavingAccountResponse.setAccountNumber(tSavingAccount.getAccountNumber());
        tSavingAccountResponse.setEndBalance(tSavingAccount.getEndBalance());
        tSavingAccountResponse.setCurrentBalance(tSavingAccount.getCurrentBalance());
        tSavingAccountResponse.setSavingId(tSavingAccount.getSavingId());
        tSavingAccountResponse.setStatus(tSavingAccount.getrStatus().getStatusId());
        tSavingAccountResponse.setCifId(tSavingAccount.getmCifId().getCifId());
        tSavingAccountResponse.setSavingName(tSavingAccount.getSavingId());


        if (tSavingAccount.getmCifId() == null) {
            tSavingAccountResponse.setCifId(tSavingAccount.getmCifId().getCifId());
        }
        if (tSavingAccount.getrStatus() == null) {
            tSavingAccountResponse.setStatus(tSavingAccount.getrStatus().getStatusId());
        } else {
            tSavingAccountResponse.setStatusId(tSavingAccount.getrStatus().getStatusId());
        }
        return tSavingAccountResponse;
    }

}
