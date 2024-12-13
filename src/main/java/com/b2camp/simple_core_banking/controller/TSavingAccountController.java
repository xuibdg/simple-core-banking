package com.b2camp.simple_core_banking.controller;


import com.b2camp.simple_core_banking.service.TSavingAccountService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tsaving")
public class TSavingAccountController {
    private TSavingAccountService tSavingAccountService;

    TSavingAccountController(TSavingAccountService tSavingAccountService) {
        this.tSavingAccountService = tSavingAccountService;
    }
}
