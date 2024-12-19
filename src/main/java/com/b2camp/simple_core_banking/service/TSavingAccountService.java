package com.b2camp.simple_core_banking.service;


import com.b2camp.simple_core_banking.dto.TSavingAccountRequest;
import com.b2camp.simple_core_banking.dto.TSavingAccountResponse;
import org.springframework.stereotype.Service;

@Service
public interface TSavingAccountService {

    TSavingAccountResponse create(TSavingAccountRequest tSavingAccountRequest);
}
