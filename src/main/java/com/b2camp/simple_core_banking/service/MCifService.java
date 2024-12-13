package com.b2camp.simple_core_banking.service;

import com.b2camp.simple_core_banking.dto.MCifRequest;
import com.b2camp.simple_core_banking.dto.MCifResponse;

public interface MCifService {

    String delete(String userId);
    MCifResponse authorization(String cifId);
}
