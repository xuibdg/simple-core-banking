package com.b2camp.simple_core_banking.controller;


import com.b2camp.simple_core_banking.dto.TSavingAccountResponse;
import com.b2camp.simple_core_banking.dto.TSavingAccountRequest;
import com.b2camp.simple_core_banking.entity.TSavingAccount;
import com.b2camp.simple_core_banking.service.TSavingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/t-saving")
public class TSavingAccountController {
    private TSavingAccountService tSavingAccountService;

    @Autowired
    TSavingAccountController(TSavingAccountService tSavingAccountService) {
        this.tSavingAccountService = tSavingAccountService;
    }

    @DeleteMapping("/{savingAccountId}")
    public String deletedById(@PathVariable String savingAccountId) {
        return tSavingAccountService.deleted(savingAccountId);
    }

    @PutMapping("/{savingAccountId}")
    public TSavingAccountResponse authorization(@PathVariable String savingAccountId) {
        return tSavingAccountService.authorization(savingAccountId);
    }

    @PostMapping
    public ResponseEntity<TSavingAccountResponse> create(@RequestBody TSavingAccountRequest request) {
        TSavingAccountResponse response = tSavingAccountService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping
    List<TSavingAccountResponse>readTSavingAccount(@Param("accountNumber")String accountNumber){
        return tSavingAccountService.readTSavingAccount(accountNumber);
    }

    @GetMapping("/{savingAccountId}")
    public Optional<TSavingAccountResponse>findBySavingAccountId(@PathVariable("savingAccountId") String savingAccountId){
        return tSavingAccountService.findBySavingAccountId(savingAccountId);
    }
}
