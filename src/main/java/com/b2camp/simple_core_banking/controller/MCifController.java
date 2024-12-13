package com.b2camp.simple_core_banking.controller;


import com.b2camp.simple_core_banking.service.MCifService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/request")
public class MCifController {

    private MCifService mCifService;

    MCifController(MCifService mCifService) {
        this.mCifService = mCifService;
    }

}
