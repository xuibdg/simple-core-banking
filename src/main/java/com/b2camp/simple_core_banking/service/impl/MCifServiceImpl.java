package com.b2camp.simple_core_banking.service.impl;

import com.b2camp.simple_core_banking.dto.MCifResponse;
import com.b2camp.simple_core_banking.entity.MCif;
import com.b2camp.simple_core_banking.repository.MCifRepository;
import com.b2camp.simple_core_banking.service.MCifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MCifServiceImpl implements MCifService {


    @Autowired
    MCifRepository mCifRepository;

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
        mCifResponse.setUpDateAt(mCif.getUpdatedAt());
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
}
