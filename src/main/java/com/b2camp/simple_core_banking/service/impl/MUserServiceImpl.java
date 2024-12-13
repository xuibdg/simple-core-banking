package com.b2camp.simple_core_banking.service.impl;

import com.b2camp.simple_core_banking.dto.MUserRequest;
import com.b2camp.simple_core_banking.dto.MUserResponse;
import com.b2camp.simple_core_banking.entity.MUser;
import com.b2camp.simple_core_banking.entity.MUserRole;
import com.b2camp.simple_core_banking.repository.MUserRepository;
import com.b2camp.simple_core_banking.repository.MUserRoleRepository;
import com.b2camp.simple_core_banking.service.MUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class   MUserServiceImpl implements MUserService {

    @Autowired
    private MUserRepository mUserRepository;

    @Autowired
    private MUserRoleRepository mUserRoleRepository;


    @Override
    public MUserResponse createUser(MUserRequest request) {
        //builder entity dengan request
        MUser mUser = new MUser();
        buildToEntity(mUser, request);
        mUserRepository.save(mUser);

        //mapping response dengan entity yg sudah di save
        return buildToResponse(mUser);
    }

    @Override
    public MUserResponse updateUser(String userId, MUserRequest request) {
        MUser mUser = mUserRepository.findById(userId).orElse(null);
        buildToEntity(mUser, request);
        mUserRepository.save(mUser);

        return buildToResponse(mUser);
    }

    @Override
    public List<MUserResponse> read(String userName) {
        List<MUser> mUser = mUserRepository.findAllByUserNameIgnoreCaseAndIsDeletedIsFalse(userName);
        List<MUserResponse> responses = mUser.stream().map(data -> buildToResponse(data)).collect(Collectors.toList());
        return responses;
    }

    @Override
    public Optional<MUserResponse> findById(String userId) {
        // Cari MUser berdasarkan userId
        MUser mUser = mUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        MUserResponse response = buildToResponse(mUser);

        return Optional.of(response);
    }

    @Override
    public String delete(String userId) {
        MUser mUser = mUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        mUser.setIsDeleted(true);
        mUserRepository.save(mUser);
        return "Success delete by userId : " + mUser.getUserName();
    }


    private MUserResponse buildToResponse(MUser mUser) {
        MUserResponse response = new MUserResponse();
        response.setUserId(mUser.getUserId());
        response.setUserName(mUser.getUserName());
        response.setEmail(mUser.getEmail());
        response.setFullname(mUser.getFullname());
        response.setIsDeleted(mUser.getIsDeleted());

        // validasi biar ga error
        if (mUser.getMUserRole() != null) {
            response.setUserRole(mUser.getMUserRole().getUserRoleId());
        } else {
            response.setUserRole(null);
        }

        return response;
    }

    private MUser buildToEntity(MUser mUser, MUserRequest request) {
        // Mengatur nilai dari request ke entity
        mUser.setUserName(request.getUserName());
        mUser.setEmail(request.getEmail());
        mUser.setPassword(request.getPassword());
        mUser.setFullname(request.getFullname());
        mUser.setIsDeleted(false);

        // Mencari MUserRole berdasarkan ID dari request
        MUserRole mUserRole = mUserRoleRepository.findById(request.getUserRoleId())
                .orElseThrow(() -> new RuntimeException("User role not found"));

        mUser.setMUserRole(mUserRole);

        // Mengembalikan entity yang sudah dibangun
        return mUser;
    }

}
