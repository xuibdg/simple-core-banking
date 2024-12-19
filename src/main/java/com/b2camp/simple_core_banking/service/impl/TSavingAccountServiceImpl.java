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


        return response;
    }
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
