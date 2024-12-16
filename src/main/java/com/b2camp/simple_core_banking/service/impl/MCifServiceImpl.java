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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class MCifServiceImpl implements MCifService {

    @Autowired
    private MCifRepository mCifRepository;

    @Autowired
    RStatusRepository rStatusRepository;

    @Autowired
    RNumberTypeRepository rNumberTypeRepository;

    @Override
    public MCifResponse updateCif(String McifID, MCifRequest request) {
        MCif mCif = mCifRepository.findById(McifID).orElse(null);
        mCifRepository.save(mCif);
        buildToEntityUpdate(mCif, request);

        return buildToResponseUpdate(mCif);
    }

    private MCifResponse buildToResponseUpdate(MCif mCif) {
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

    private void buildToEntityUpdate(MCif mCif, MCifRequest request) {
        mCif.setCustomerName(request.getCustomerName());
        mCif.setPhoneNumber(request.getPhoneNumber());
        mCif.setAddress(request.getAddress());
        mCif.setEmail(request.getEmail());
        mCif.setIdNumber(request.getIdNumber());
        mCif.setDateOfBirth(request.getDateOfBirth());

        RStatus rStatus = rStatusRepository.findById("1").orElseThrow(() -> new RuntimeException("Rstatus role not found"));
        mCif.setrStatus(rStatus);
        mCif.setIdNumber(request.getIdNumber());
        RNumberType rNumberType = rNumberTypeRepository.findById(request.getIdNumberType()).orElseThrow(() -> new RuntimeException("RNumbertype role not found"));
        mCif.setrNumberType(rNumberType);
        mCif.setCreatedAt(Timestamp.from(Instant.now()));
        mCif.setDeleted(false);
    }
}
