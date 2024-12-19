package com.b2camp.simple_core_banking.service.impl;

import com.b2camp.simple_core_banking.dto.MCifResponse;
import com.b2camp.simple_core_banking.entity.MCif;
import com.b2camp.simple_core_banking.repository.MCifRepository;
import com.b2camp.simple_core_banking.dto.MCifRequest;
import com.b2camp.simple_core_banking.entity.RNumberType;
import com.b2camp.simple_core_banking.entity.RStatus;
import com.b2camp.simple_core_banking.repository.RNumberTypeRepository;
import com.b2camp.simple_core_banking.repository.RStatusRepository;
import com.b2camp.simple_core_banking.service.MCifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.sql.Timestamp;
import java.time.Instant;

@Slf4j
@Service
public class MCifServiceImpl implements MCifService {


    @Autowired
    MCifRepository mCifRepository;

    @Autowired
    RStatusRepository rStatusRepository;

    @Autowired
    RNumberTypeRepository rNumberTypeRepository;

    @Override
    public List<MCifResponse> reads(String customerName) {
        List<MCif> mCifs = mCifRepository.findAllByCustomerNameAndIsDeletedFalse(customerName);
        List<MCifResponse> mCifResponses = mCifs.stream().map(data -> buildToResponse(data)).collect(Collectors.toList());
        return mCifResponses;
    }

    @Override
    public Optional<MCifResponse> findByCifId(String cifId) {
        MCif mCif = mCifRepository.findByCifIdAndIsDeletedFalse(cifId)
                .orElseThrow(() -> new RuntimeException("Data tidak ditemukan untuk CIF ID: " + cifId));
        return Optional.of(buildToResponse(mCif));
    }


    public MCifResponse buildToResponse(MCif mCif) {
        MCifResponse mCifResponse = new MCifResponse();
        mCifResponse.setCifId(mCif.getCifId());
        mCifResponse.setCustomerName(mCif.getCustomerName());
        mCifResponse.setDateOfBirth(mCif.getDateOfBirth());
        mCifResponse.setAddress(mCif.getAddress());
        mCifResponse.setPhoneNumber(mCif.getPhoneNumber());
        mCifResponse.setEmail(mCif.getEmail());
        mCifResponse.setIdNumber(mCif.getIdNumber());
        mCifResponse.setIdNumberType(mCif.getrNumberType().getTypeId());
        mCifResponse.setDeleted(mCif.isDeleted());
        mCifResponse.setCreatedAt(mCif.getAuthorizationAt());
        mCifResponse.setUpDateAt(mCif.getUpdateAt());
        mCifResponse.setAuthorizationAt(mCif.getAuthorization_at());

        if (mCif.getrStatus() != null) {
            mCifResponse.setStatusId(mCif.getrStatus().getStatusId());
            mCifResponse.setStatusName(mCif.getrStatus().getStatusName());
        }
        if (mCif.getmUserAuthorizationBy() != null) {
            mCifResponse.setAuthorizationBy(mCif.getmUserAuthorizationBy().getUserId());
        }
        if (mCif.getCreatedBy() != null) {
            mCifResponse.setCreatedBy(mCif.getCreatedBy().getUserId());
        }
        if (mCif.getUpdatedBy() != null) {
            mCifResponse.setUpDateBy(mCif.getUpdatedBy().getUserId());
        }
        if (mCif.getrNumberType() != null) {
            mCifResponse.setIdNumberType(mCif.getrNumberType().getTypeId());
        }
        return mCifResponse;
    }

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
