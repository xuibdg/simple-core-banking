package com.b2camp.simple_core_banking.service;

import com.b2camp.simple_core_banking.dto.MCifResponse;
import org.springframework.stereotype.Service;
import com.b2camp.simple_core_banking.dto.MCifRequest;


import java.util.List;
import java.util.Optional;

@Service
public interface MCifService {
    List<MCifResponse> reads(String customerName);

    Optional<MCifResponse> findByCifId(String cifId);
    MCifResponse createCif(MCifRequest request);
}
