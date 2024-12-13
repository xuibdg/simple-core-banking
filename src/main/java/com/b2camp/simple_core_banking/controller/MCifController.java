package com.b2camp.simple_core_banking.controller;


import com.b2camp.simple_core_banking.dto.MCifRequest;
import com.b2camp.simple_core_banking.dto.MCifResponse;
import com.b2camp.simple_core_banking.service.MCifService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/request")
public class MCifController {

    private MCifService mCifService;

    MCifController(MCifService mCifService) {
        this.mCifService = mCifService;
    }

    @PostMapping
    public MCifResponse createCif (@RequestBody MCifRequest request){
        return mCifService.createCif(request);
    }

}
