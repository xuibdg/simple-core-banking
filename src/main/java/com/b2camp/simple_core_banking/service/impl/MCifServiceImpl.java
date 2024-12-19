package com.b2camp.simple_core_banking.service.impl;

import com.b2camp.simple_core_banking.dto.MCifRequest;
import com.b2camp.simple_core_banking.dto.MCifResponse;
import com.b2camp.simple_core_banking.entity.MCif;
import com.b2camp.simple_core_banking.entity.RNumberType;
import com.b2camp.simple_core_banking.entity.RStatus;
import com.b2camp.simple_core_banking.repository.MCifRepository;
import com.b2camp.simple_core_banking.repository.RNumberTypeRepository;
import com.b2camp.simple_core_banking.repository.RStatusRepository;
import com.b2camp.simple_core_banking.service.MCifService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
@Slf4j
@Service
public class MCifServiceImpl implements MCifService {

    private static final Logger log = LogManager.getLogger(MCifServiceImpl.class);
    @Autowired
    MCifRepository mCifRepository;

    @Autowired
    RStatusRepository rStatusRepository;

    @Autowired
    RNumberTypeRepository rNumberTypeRepository;




    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public MCifResponse createCif(MCifRequest request) {
        MCif mCif = new MCif();
        buildEntity(mCif, request);
       log.info("Data SUCCES : {}",mCif.getPhoneNumber());
        mCifRepository.save(mCif);
        return buildResponseForCreate(mCif);
    }

    private MCifResponse buildResponseForCreate(MCif mCif) {
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

    private void buildEntity(MCif mCif, MCifRequest request) {
        mCif.setCustomerName(request.getCustomerName());
        mCif.setPhoneNumber(request.getPhoneNumber());
        mCif.setAddress(request.getAddress());
        mCif.setEmail(request.getEmail());
        mCif.setIdNumber(request.getIdNumber());
        mCif.setDateOfBirth(request.getDateOfBirth());

        RStatus rStatus = rStatusRepository.findById("3").orElseThrow(() -> new RuntimeException("Rstatus role not found"));
        mCif.setrStatus(rStatus);
        mCif.setIdNumber(request.getIdNumber());

        RNumberType rNumberType = rNumberTypeRepository.findById(request.getIdNumberType()).orElseThrow(() -> new RuntimeException("RNumbertype role not found"));
        mCif.setrNumberType(rNumberType);
        mCif.setCreatedAt(Timestamp.from(Instant.now()));
        mCif.setDeleted(false);

    }
}
