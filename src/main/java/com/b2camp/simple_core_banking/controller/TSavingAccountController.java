package com.b2camp.simple_core_banking.controller;

import com.b2camp.simple_core_banking.dto.TSavingAccountRequest;
import com.b2camp.simple_core_banking.dto.TSavingAccountResponse;
import com.b2camp.simple_core_banking.service.TSavingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tsaving")
public class TSavingAccountController {

    private final TSavingAccountService tSavingAccountService;

    @Autowired
    public TSavingAccountController(TSavingAccountService tSavingAccountService) {
        this.tSavingAccountService = tSavingAccountService;
    }

    @PostMapping("/")
    public ResponseEntity<TSavingAccountResponse> create(@RequestBody TSavingAccountRequest request) {
        TSavingAccountResponse response = tSavingAccountService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}

