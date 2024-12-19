package com.b2camp.simple_core_banking.controller;


import com.b2camp.simple_core_banking.dto.TSavingAccountRequest;
import com.b2camp.simple_core_banking.dto.TSavingAccountResponse;
import com.b2camp.simple_core_banking.service.TSavingAccountService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/saving-account")
public class TSavingAccountController {
    private TSavingAccountService tSavingAccountService;

   TSavingAccountController(TSavingAccountService tSavingAccountService) {
        this.tSavingAccountService = tSavingAccountService;
    }

    @PutMapping("/{savingAccountId}")
    public TSavingAccountResponse updateTSavingAccount(@PathVariable String savingAccountId,
                                                       @RequestBody TSavingAccountRequest request) {
        return tSavingAccountService.updateSavingAccount(savingAccountId, request);
    }
}
