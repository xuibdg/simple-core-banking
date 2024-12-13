package com.b2camp.simple_core_banking.controller;


import com.b2camp.simple_core_banking.dto.MCifResponse;
import com.b2camp.simple_core_banking.service.MCifService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/request")
public class MCifController {

    private MCifService mCifService;

    MCifController(MCifService mCifService) {
        this.mCifService = mCifService;
    }


    @DeleteMapping("/{cifId}")
    public String delete(@PathVariable String cifId) {
        return mCifService.delete(cifId);
    }

    @PutMapping("/{cifId}")
    public MCifResponse authorization(@PathVariable String cifId) {
        return mCifService.authorization(cifId);
    }
}
