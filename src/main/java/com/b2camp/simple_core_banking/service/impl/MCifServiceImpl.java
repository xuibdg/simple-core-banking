package com.b2camp.simple_core_banking.service.impl;

import com.b2camp.simple_core_banking.dto.MCifResponse;
import com.b2camp.simple_core_banking.entity.MCif;
import com.b2camp.simple_core_banking.entity.TSavingAccount;
import com.b2camp.simple_core_banking.enums.Status;
import com.b2camp.simple_core_banking.repository.MCifRepository;
import com.b2camp.simple_core_banking.dto.MCifRequest;
import com.b2camp.simple_core_banking.entity.RNumberType;
import com.b2camp.simple_core_banking.entity.RStatus;
import com.b2camp.simple_core_banking.repository.RNumberTypeRepository;
import com.b2camp.simple_core_banking.repository.RStatusRepository;
import com.b2camp.simple_core_banking.service.MCifService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.sql.Timestamp;
import java.time.Instant;

@Service
@Slf4j
public class MCifServiceImpl implements MCifService{


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
        mCifResponse.setIdNumberType(mCif.getRNumberType().getTypeId());
        mCifResponse.setDeleted(mCif.isDeleted());
        mCifResponse.setCreatedAt(mCif.getAuthorizationAt());
        mCifResponse.setUpDateAt(mCif.getUpdatedAt());
        mCifResponse.setAuthorizationAt(mCif.getAuthorizationAt());

        if (mCif.getRStatus() != null) {
            mCifResponse.setStatusId(mCif.getRStatus().getStatusId());
            mCifResponse.setCustomerName(mCif.getRStatus().getStatusName());
        }
        if (mCif.getMUserAuthorizationBy() != null) {
            mCifResponse.setAuthorizationBy(mCif.getMUserAuthorizationBy().getUserId());
        }
        if (mCif.getCreatedBy() != null) {
            mCifResponse.setCreatedBy(mCif.getCreatedBy().getUserId());
        }
        if (mCif.getUpdatedBy() != null) {
            mCifResponse.setUpDateBy(mCif.getUpdatedBy().getUserId());
        }
        if (mCif.getRNumberType() != null) {
            mCifResponse.setIdNumberType(mCif.getRNumberType().getTypeId());
        }
        return mCifResponse;
    }

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
        response.setStatusId(mCif.getRStatus().getStatusId());
        response.setAuthorizationAt(mCif.getAuthorizationAt());
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

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public MCifResponse createCif(MCifRequest request) {
        MCif mCif = new MCif();
        buildEntity(mCif, request);
        log.info("Data SUCCESS: {}", mCif.getPhoneNumber());
        mCifRepository.save(mCif);
        return buildResponseForCreate(mCif);
    }

    @Override
    public String delete(String cifId) {
        MCif mCif = mCifRepository.findByCifIdAndIsDeletedFalse(cifId)
                .orElseThrow(() -> new RuntimeException("Data Not Faound"));
        if (mCif.getRStatus().getStatusId().equals(Status.INACTIVE.getKey())) {
            throw new RuntimeException("Status Active");
        }
        mCif.setDeleted(true);
        mCifRepository.save(mCif);
        log.info("MCif deleted successfully for Account Number: {}, cifId: {}",
                mCif.getIdNumber(),
                mCif.getCifId());
        mCif.setUpdatedAt(Timestamp.from(Instant.now()));
        return "Success delete by MCif Id : " + mCif.getCifId();
    }

    @Override
    public MCifResponse authorization(String cifId) {
        MCif mCif = mCifRepository.findByCifIdAndIsDeletedFalse(cifId)
                .orElseThrow(() -> new RuntimeException(" Data MCif Not Found"));

        if (mCif.getRStatus().getStatusId().equals(Status.ACTIVE.getKey())) {
            throw new RuntimeException("Data Already Active");
        }
        RStatus rStatus = rStatusRepository.findById(Status.ACTIVE.getKey()).orElseThrow(() -> new RuntimeException("RStatus Not Found "));
        mCif.setRStatus(rStatus);
        mCif.setAuthorizationAt(Timestamp.from(Instant.now()));
        mCifRepository.save(mCif);
        log.info("Authorization completed for CifId: {}, Status: {}",
                cifId,
                rStatus.getStatusId());
        return buildToResponseForUpdate(mCif);
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
        response.setStatusId(mCif.getRStatus().getStatusId());
        response.setAuthorizationAt(mCif.getAuthorizationAt());
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
        mCif.setRStatus(rStatus);
        mCif.setIdNumber(request.getIdNumber());

        RNumberType rNumberType = rNumberTypeRepository.findById(request.getIdNumberType()).orElseThrow(() -> new RuntimeException("RNumbertype role not found"));
        mCif.setRNumberType(rNumberType);
        mCif.setCreatedAt(Timestamp.from(Instant.now()));
        mCif.setDeleted(false);

    }
}
