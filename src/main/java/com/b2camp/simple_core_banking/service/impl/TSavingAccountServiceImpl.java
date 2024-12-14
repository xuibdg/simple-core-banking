package com.b2camp.simple_core_banking.service.impl;

import com.b2camp.simple_core_banking.entity.TSavingAccount;
import com.b2camp.simple_core_banking.repository.TSavingAccountRepository;
import com.b2camp.simple_core_banking.service.TSavingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TSavingAccountServiceImpl implements TSavingAccountService {

    @Autowired
    private TSavingAccountRepository tSavingAccountRepository;

    @Override
    public String deleted(String savingAccountId) {
        TSavingAccount tSavingAccount =tSavingAccountRepository.findById(savingAccountId)
                .orElseThrow(() -> new RuntimeException("User Tidak Ada"));
        tSavingAccount.setIsDeleted(true);;
        tSavingAccountRepository.save(tSavingAccount);
        return "Success delete by Saving Account Id : " + tSavingAccount.getSavingAccountId();


    }
}
