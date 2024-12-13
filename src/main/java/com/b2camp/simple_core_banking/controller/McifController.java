package com.b2camp.simple_core_banking.controller;


import com.b2camp.simple_core_banking.service.MCifService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/request")
public class McifController {

    private MCifService mCifService;

    McifController(MCifService mCifService) {
        this.mCifService = mCifService;
    }

}
