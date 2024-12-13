package com.b2camp.simple_core_banking.service.impl;

import com.b2camp.simple_core_banking.dto.MCifRequest;
import com.b2camp.simple_core_banking.dto.MCifResponse;
import com.b2camp.simple_core_banking.dto.MUserResponse;
import com.b2camp.simple_core_banking.entity.MCif;
import com.b2camp.simple_core_banking.entity.MUser;
import com.b2camp.simple_core_banking.entity.RStatus;
import com.b2camp.simple_core_banking.repository.MCifRepository;
import com.b2camp.simple_core_banking.repository.MUserRepository;
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
    private RStatusRepository rStatusRepository;


    @Override
    public String delete(String cifId) {
        MCif mCif = mCifRepository.findById(cifId)
                .orElseThrow(() -> new RuntimeException("Not Found"));
        mCif.setDeleted(true);
        mCifRepository.save(mCif);
        return "Success Deleted : " + mCif.getCustomerName();
    }

    @Override
    public MCifResponse authorization(String cifId) {
        // Cari authorization berdasarkan userId
        MCif mCif = mCifRepository.findById(cifId)
                .orElseThrow(() -> new RuntimeException("Cif Not Found"));
        if (mCif.getrStatus().getStatusId().equals("2")){
            throw new RuntimeException("Status Ini Sudah Active");
        }
        RStatus rStatus = rStatusRepository.findById("2")
                .orElseThrow(() -> new RuntimeException("Rstatus Not Found"));
        mCif.setrStatus(rStatus);
        mCif.setAuthorization_at(Timestamp.from(Instant.now()));
        mCifRepository.save(mCif);
        return buildToResponse(mCif);
    }


    private MCifResponse buildToResponse(MCif mCif) {
        MCifResponse response = new MCifResponse();
        mCif.setCustomerName(response.getCustomerName());
        mCif.setDateOfBirth(response.getDateOfBirth());
        mCif.setAddress(response.getAddress());
        mCif.setPhoneNumber(response.getPhoneNumber());
        mCif.setEmail(response.getEmail());
        mCif.setIdNumber(response.getIdNumber());

        // validasi biar ga error
        if (mCif.getrStatus() != null) {
            response.setCifId(mCif.getrStatus().getStatusId());
        } else {
            response.setStatusId(null);
        }

        return response;
    }

    private MCif buildToEntity(MCif mCif, MCifRequest request) {
        // Mengatur nilai dari request ke entity
        mCif.setCustomerName(request.getCustomerName());
        mCif.setDateOfBirth(request.getDateOfBirth());
        mCif.setAddress(request.getAddress());
        mCif.setPhoneNumber(request.getPhoneNumber());
        mCif.setEmail(request.getEmail());
        mCif.setIdNumber(request.getIdNumber());
        mCif.setDeleted(false);

        RStatus rStatus = rStatusRepository.findById(request.getStatusId())
                .orElseThrow(() -> new RuntimeException("User role not found"));

        mCif.setCifId(rStatus.getStatusId());

        // Mengembalikan entity yang sudah dibangun
        return mCif;
    }
}
