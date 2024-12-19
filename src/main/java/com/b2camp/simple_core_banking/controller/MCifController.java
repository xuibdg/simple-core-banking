package com.b2camp.simple_core_banking.controller;

import com.b2camp.simple_core_banking.dto.MCifRequest;
import com.b2camp.simple_core_banking.dto.MCifResponse;
import com.b2camp.simple_core_banking.repository.MCifRepository;
import com.b2camp.simple_core_banking.service.MCifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/request")
public class MCifController {
    @Autowired
    private MCifService mCifService;

    @Autowired
    private MCifRepository mCifRepository;

    MCifController(MCifService mCifService) {
        this.mCifService = mCifService;
    }

    @PutMapping("/{id}")
    public MCifResponse mCifRepository (@PathVariable String id,
                                          @RequestBody MCifRequest request){
        return mCifService.updateCif(id, request);
    }
}
