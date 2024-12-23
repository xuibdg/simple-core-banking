package com.b2camp.simple_core_banking.service.impl;

import com.b2camp.simple_core_banking.dto.TSavingAccountRequest;
import com.b2camp.simple_core_banking.dto.TSavingAccountResponse;
import com.b2camp.simple_core_banking.entity.MCif;
import com.b2camp.simple_core_banking.entity.MSaving;
import com.b2camp.simple_core_banking.entity.TSavingAccount;
import com.b2camp.simple_core_banking.repository.MCifRepository;
import com.b2camp.simple_core_banking.repository.MSavingRepository;
import com.b2camp.simple_core_banking.repository.MUserRepository;
import com.b2camp.simple_core_banking.repository.TSavingAccountRepository;
import com.b2camp.simple_core_banking.service.TSavingAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Objects;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import java.util.Objects;

import static com.b2camp.simple_core_banking.service.impl.MCifServiceImpl.log;

@Slf4j
@Service
public class TSavingAccountServiceImpl implements TSavingAccountService {

    @Autowired
    private TSavingAccountRepository tSavingAccountRepository;

    @Autowired
    private MUserRepository mUserRepository;

    @Autowired
    private MCifRepository mCifRepository;

    @Autowired
    private MSavingRepository mSavingRepository;

    @Autowired
    private MUserRepository mUserRepository;

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
        tSavingAccountResponse.setCifId(tSavingAccount.getmCifId().getCifId());
        tSavingAccountResponse.setSavingName(tSavingAccount.getmSaving().getSavingName());

        if (tSavingAccount.getUpdatedAt != null) {
            tSavingAccountResponse.setUpdatedAt(tSavingAccount.getUpdateAt());
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

        if (tSavingAccount.getrStatus() != null) {
            tSavingAccountResponse.setStatusId(tSavingAccount.getrStatus().getStatusId());
            tSavingAccountResponse.setStatusName(tSavingAccount.getrStatus().getStatusName());
        }
        if (tSavingAccount.getmCifId() != null) {
            tSavingAccountResponse.setCifId(tSavingAccount.getmCifId().getCifId());
        }
        if (tSavingAccount.getmSaving() != null) {
            tSavingAccountResponse.setSavingId(tSavingAccount.getmSaving().getSavingId());
        }

        return tSavingAccountResponse;
    }

    @Override
    @Transactional (propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public TSavingAccountResponse updateSavingAccount(String savingAccountId, TSavingAccountRequest request) {
        TSavingAccount tSavingAccount = tSavingAccountRepository
                .findById(savingAccountId).orElse(null);
        log.error("TSavingAccountServiceImpl UpdateSavingAccount: {}" ,tSavingAccount);
        buildEntity(tSavingAccount,request);
        log.info("TSavingAccountServiceImpl UpdateSavingAccount Save");
        tSavingAccountRepository.save(tSavingAccount);
        return buildResponse(tSavingAccount);
    }

    private TSavingAccountResponse buildResponse(TSavingAccount tSavingAccount) {
        TSavingAccountResponse response = new TSavingAccountResponse();
        response.setSavingAccountId(tSavingAccount.getSavingAccountId());
        response.setAccountNumber(tSavingAccount.getAccountNumber());
        response.setCifId(tSavingAccount.getMCif().getCifId());;
        response.setBeginBalance(tSavingAccount.getBeginBalance());
        response.setEndBalance(tSavingAccount.getEndBalance());
        response.setCurrentBalance(tSavingAccount.getCurrentBalance());
        response.setStatusId(tSavingAccount.getrStatus().getStatusId());
        response.setStatusName(tSavingAccount.getrStatus().getStatusName());
        response.setDeleted(tSavingAccount.isDeleted());
        response.setAuthorizationAt(tSavingAccount.getAuthorizationAt());
        response.setAuthorizationBy(!Objects.isNull(tSavingAccount.getmUserAuthorizationBy()) ? tSavingAccount.getmUserAuthorizationBy().getUserId() : "");
        if(!Objects.isNull(tSavingAccount.getMSaving())){
            response.setSavingId(tSavingAccount.getMSaving().getSavingName());
            response.setSavingName(tSavingAccount.getMSaving().getSavingName());
        }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
            public Optional<TSavingAccountResponse> findBySavingAccountId(String savingAccountId) {
        TSavingAccount tSavingAccount = tSavingAccountRepository.findById(savingAccountId)
                .orElseThrow(() -> new RuntimeException("Data saving id tidak ada: " + savingAccountId));
        log.info("Data Terlihat. {}", tSavingAccount.getSavingAccountId());
        return  Optional.of(buildToResponseAccount(tSavingAccount));

        return response;
    }

    private void buildToEntityForCreate(TSavingAccount tSavingAccount, TSavingAccountRequest request) {
        log.info("TSavingAccountServiceImpl buildToEntity, process build to entity TSavingAccount : {}", tSavingAccount.getAccountNumber());
        tSavingAccount.setAccountNumber(request.getAccountNumber());
        tSavingAccount.setBeginBalance(request.getBeginBalance());
        tSavingAccount.setEndBalance(request.getEndBalance());
        tSavingAccount.setCurrentBalance(request.getCurrentBalance());
        tSavingAccount.setIsDeleted(false);
//        tSavingAccount.setCre(Timestamp.from(Instant.now()));


        log.info("TSavingAccountServiceImpl buildToEntity, process search data rStatusId from : {}", tSavingAccount);
        RStatus rStatus = rStatusRepository.findById(Status.PENDING.getKey())
                .orElseThrow(() -> new RuntimeException("Not Found"));
        tSavingAccount.setrStatus(rStatus);

        log.info("TSavingAccountServiceImpl buildToEntity, process search data mCif from : {} ", tSavingAccount);
        MCif mCif = mCifRepository.findById(request.getCifId())
                .orElseThrow(() -> new RuntimeException("cifId not found"));
        tSavingAccount.setmCifId(mCif);

        log.info("TSavingAccountServiceImpl buildToEntity, process search data mSaving from : {} ", tSavingAccount);
        MSaving mSaving = mSavingRepository.findById(request.getSavingId())
                .orElseThrow(()-> new RuntimeException("savingId not found"));
        tSavingAccount.setmSaving(mSaving);

    }
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public TSavingAccountResponse updateSavingAccount(String savingAccountId, TSavingAccountRequest request) {
        TSavingAccount tSavingAccount = tSavingAccountRepository
                .findById(savingAccountId).orElse(null);
        log.error("TSavingAccountServiceImpl UpdateSavingAccount: {}" ,tSavingAccount);
        buildEntity(tSavingAccount,request);
        log.info("TSavingAccountServiceImpl UpdateSavingAccount Save");
        tSavingAccountRepository.save(tSavingAccount);
        return buildResponse(tSavingAccount);
    }

    private TSavingAccountResponse buildResponse(TSavingAccount tSavingAccount) {
        TSavingAccountResponse response = new TSavingAccountResponse();
        response.setSavingAccountId(tSavingAccount.getSavingAccountId());
        response.setAccountNumber(tSavingAccount.getAccountNumber());
        response.setCifId(tSavingAccount.getmCifId().getCifId());;
        response.setBeginBalance(tSavingAccount.getBeginBalance());
        response.setEndBalance(tSavingAccount.getEndBalance());
        response.setCurrentBalance(tSavingAccount.getCurrentBalance());
        response.setStatusId(tSavingAccount.getrStatus().getStatusId());
        response.setStatusName(tSavingAccount.getrStatus().getStatusName());
        response.setDeleted(tSavingAccount.isDeleted());
        response.setAuthorizationAt(tSavingAccount.getAuthorizationAt());
        response.setAuthorizationBy(!Objects.isNull(tSavingAccount.getmUserAuthorizationBy()) ? tSavingAccount.getmUserAuthorizationBy().getUserId() : "");
        if(!Objects.isNull(tSavingAccount.getmSaving())){
            response.setSavingId(tSavingAccount.getmSaving().getSavingName());
            response.setSavingName(tSavingAccount.getmSaving().getSavingName());
        }


        return response;
    }
    @Transactional (propagation = Propagation.REQUIRED)
    private void buildEntity (TSavingAccount tSavingAccount, TSavingAccountRequest request){
        log.info("TSavingAccountServiceImpl buidEntity Process Update");
        tSavingAccount.setAccountNumber(request.getAccountNumber());
        MCif mCif = mCifRepository.findById(request.getCifId())
                .orElseThrow(() -> new RuntimeException("MCif not found"));
        tSavingAccount.setmCifId(mCif);
        MSaving mSaving = mSavingRepository.findById(request.getSavingId())
                .orElseThrow(()-> new RuntimeException("MSaving not Found"));
        tSavingAccount.setmSaving(mSaving);
        log.info("TSavingAccountServiceImpl buildentity : {}" ,tSavingAccount);
        @Transactional (propagation = Propagation.REQUIRED)
        private TSavingAccountRequest buildEntity (TSavingAccount tSavingAccount, TSavingAccountRequest request){
        log.info("TSavingAccountServiceImpl buidEntity Process Update");
            tSavingAccount.setAccountNumber(request.getAccountNumber());
            MCif mCif = mCifRepository.findById(request.getCifId())
                    .orElseThrow(() -> new RuntimeException("MCif not found"));
            tSavingAccount.setMCif(mCif);
            MSaving mSaving = mSavingRepository.findById(request.getSavingId())
                    .orElseThrow(()-> new RuntimeException("MSaving not Found"));
            tSavingAccount.setMSaving(mSaving);
            log.info("TSavingAccountServiceImpl buildentity : {}" ,tSavingAccount);
        return request;
    }
}
