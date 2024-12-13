package com.b2camp.simple_core_banking.service;

import com.b2camp.simple_core_banking.dto.MCifRequest;
import com.b2camp.simple_core_banking.dto.MCifResponse;


public interface MCifService {
   MCifResponse createCif(MCifRequest request);
}
