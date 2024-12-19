package com.b2camp.simple_core_banking.controller;


import com.b2camp.simple_core_banking.dto.MCifResponse;
import com.b2camp.simple_core_banking.service.MCifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cif")
public class MCifController {

    private MCifService mCifService;

    @Autowired
    MCifController(MCifService mCifService) {
        this.mCifService = mCifService;
    }

    @GetMapping
    public List<MCifResponse> readMCifList(@Param("customerName") String customerName) {
        return mCifService.reads(customerName);
    }

    @GetMapping("/{cifId}")
    public Optional<MCifResponse> findByCifId(@PathVariable String cifId) {
        return mCifService.findByCifId(cifId);
    }
}
