package com.b2camp.simple_core_banking.service.impl;

import com.b2camp.simple_core_banking.dto.MCifRequest;
import com.b2camp.simple_core_banking.dto.MCifResponse;
import com.b2camp.simple_core_banking.entity.MCif;
import com.b2camp.simple_core_banking.repository.MCifRepository;
import com.b2camp.simple_core_banking.service.MCifService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class MCifServiceImpl implements MCifService {

    private static final Logger log = LoggerFactory.getLogger(MCifServiceImpl.class);
    @Autowired
    private MCifRepository mCifRepository;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ,propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public MCifResponse updateCif(String cifId, MCifRequest request) {
        MCif mCif = mCifRepository.findById(cifId).orElse(null);
        buildToEntityForUpdate(mCif, request);
        log.info("MCifServiceImpl updateCif, (Successfully process update the save to) : {}", mCif);
        mCifRepository.save(mCif);
        return buildToResponseForUpdate(mCif);
    }

    private MCifResponse buildToResponseForUpdate(MCif mCif) {
        MCifResponse response = new MCifResponse();
        response.setCifId(mCif.getCifId());
        response.setCustomerName(mCif.getCustomerName());
        response.setAddress(mCif.getAddress());
        response.setEmail(mCif.getEmail());
        response.setIdNumber(mCif.getIdNumber());
        response.setPhoneNumber(mCif.getPhoneNumber());
        response.setIdNumberType(mCif.getIdNumber());
        response.setStatusId(mCif.getrStatus().getStatusId());
        response.getAuthorizationAt(mCif.getAuthorization_at());
        response.setDeleted(false);
        return response;
    }

    private void buildToEntityForUpdate(MCif mCif, MCifRequest request) {
        log.info("MCifServiceImpl buildToEntityForUpdate, process build to entity MCif : {}", mCif.getEmail());
        mCif.setCustomerName(request.getCustomerName());
        mCif.setPhoneNumber(request.getPhoneNumber());
        mCif.setAddress(request.getAddress());
        mCif.setEmail(request.getEmail());
        mCif.setIdNumber(request.getIdNumber());
        mCif.setDateOfBirth(request.getDateOfBirth());

    }
}
