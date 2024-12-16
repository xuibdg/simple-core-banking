package com.b2camp.simple_core_banking.service;

import com.b2camp.simple_core_banking.dto.MCifRequest;
import com.b2camp.simple_core_banking.dto.MCifResponse;
import org.springframework.stereotype.Service;

@Service
public interface MCifService {
    MCifResponse updateCif(String McifID, MCifRequest request);
}
