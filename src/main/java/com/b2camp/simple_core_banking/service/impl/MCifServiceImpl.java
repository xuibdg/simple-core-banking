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
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;

@Slf4j
@Service
public class MCifServiceImpl implements MCifService {

    static final Logger log = LoggerFactory.getLogger(MCifServiceImpl.class);
    @Autowired
    private MCifRepository mCifRepository;

    @Autowired
    private RStatusRepository rStatusRepository;



    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ,rollbackFor = Exception.class)
    public String delete(String cifId) {
        log.info("MCifServiceImpl Delete , Process Delete : {}", cifId);
        MCif mCif = mCifRepository.findById(cifId)
                .orElseThrow(() -> new RuntimeException("Not Found"));
        validationDeleted(cifId);
        mCif.setDeleted(true);
        log.info("MCifServiceImpl Delete , Success Delete : {}", mCif);
        mCifRepository.save(mCif);
        return "Success Deleted : " + mCif.getCustomerName();
    }
    @Transactional(isolation = Isolation.SERIALIZABLE ,propagation = Propagation.REQUIRES_NEW)
    public String validationDeleted (String cifId) {
        RStatus rStatus = rStatusRepository.findById("2").orElseThrow(() -> new RuntimeException("Not Found"));
        MCif mCif = mCifRepository.findById(cifId).orElseThrow(()-> new RuntimeException("Not Found"));
            if (mCif.getrStatus().getStatusId().equals("2")) {
                throw new RuntimeException("Status Ini Sudah Active");
            }
            mCif.setrStatus(rStatus);
            return cifId;

    }

    @Override
    @Transactional (propagation = Propagation.REQUIRED)
    public MCifResponse authorization(String cifId) {
        log.info("MCifServiceImpl authorization, process set up authorization");
        // Cari authorization berdasarkan userId
        MCif mCif = mCifRepository.findById(cifId)
                .orElseThrow(() -> new RuntimeException("Cif Not Found"));
        validationId(cifId);
        RStatus rStatus = rStatusRepository.findById("4")
                .orElseThrow(() -> new RuntimeException("Rstatus Not Found"));
        mCif.setrStatus(rStatus);
        mCif.setAuthorization_at(Timestamp.from(Instant.now()));
        mCifRepository.save(mCif);
        return buildToResponse(mCif, rStatus);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String validationId (String cifId) {
        MCif mCif = mCifRepository.findById(cifId)
                .orElseThrow(()-> new RuntimeException("MCif Not Found"));
        if (mCif.getrStatus().getStatusId().equals("4")){
            throw new RuntimeException("Status Ini Sudah Active");
        }
        log.info("MCifServiceImpl validationId, process Success ");
        return "validasi Success";
    }

    @Transactional
    private MCifResponse buildToResponse(MCif mCif,RStatus rStatus) {
        log.info("MCifServiceImpl buildToResponse, process build to Response mCif");
        MCifResponse response = new MCifResponse();
        response.setCustomerName(mCif.getCustomerName());
        response.setDateOfBirth(mCif.getDateOfBirth());
        response.setAddress(mCif.getAddress());
        response.setPhoneNumber(mCif.getPhoneNumber());
        response.setEmail(mCif.getEmail());
        response.setIdNumber(mCif.getIdNumber());
        response.setStatusId(rStatus.getStatusId());

        // validasi biar ga error
        log.info("MCifService buildToResponse, Process data Response : {}", mCif);
        if (mCif.getrStatus() != null) {
            response.setCifId(mCif.getrStatus().getStatusId());
        } else {
            response.setStatusId(null);
        }
        log.info("MCifService buildToResponse ,Success : {}",mCif);
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
