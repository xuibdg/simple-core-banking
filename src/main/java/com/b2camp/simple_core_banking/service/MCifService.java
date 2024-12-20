package com.b2camp.simple_core_banking.service;

import com.b2camp.simple_core_banking.dto.MCifRequest;
import com.b2camp.simple_core_banking.dto.MCifResponse;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

public interface MCifService {
    List<MCifResponse> reads(String customerName);

    Optional<MCifResponse> findByCifId(String cifId);
    MCifResponse updateCif(String cifId, MCifRequest request);
    MCifResponse createCif(MCifRequest request);
}
