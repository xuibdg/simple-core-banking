package com.b2camp.simple_core_banking.service.impl;

import com.b2camp.simple_core_banking.dto.MUserRequest;
import com.b2camp.simple_core_banking.dto.MUserResponse;
import com.b2camp.simple_core_banking.entity.MUser;
import com.b2camp.simple_core_banking.repository.MUserRepository;
import com.b2camp.simple_core_banking.service.MUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MUserServiceImpl implements MUserService {

    @Autowired
    private MUserRepository mUserRepository;


    @Override
    public MUserResponse createUser(MUserRequest request) {
        //builder entity dengan request
        MUser mUser = new MUser();
        buildToEntity(mUser,request);
        mUserRepository.save(mUser);

        //mapping response dengan entity yg sudah di save
        MUserResponse response = new MUserResponse();
        buildToResponse(response, mUser);
        return response;
    }

    @Override
    public MUserResponse updateUser(String userId, MUserRequest request) {
        MUser mUser = mUserRepository.findById(userId).orElse(null);
        buildToEntity(mUser,request);
        mUserRepository.save(mUser);

        MUserResponse response = new MUserResponse();
        buildToResponse(response, mUser);
        return response;
    }

    @Override
    public List<MUserResponse> read(String userName) {
        List<MUser> mUser = mUserRepository.findAllByUserNameIgnoreCaseAndIsDeletedIsFalse(userName);
        List<MUserResponse> responses = mUser.stream().map(data -> {
                    MUserResponse response = new MUserResponse();
                    return buildToResponse(response, data);
                }
        ).collect(Collectors.toList());
        return responses;
    }

    @Override
    public Optional<MUserResponse> findById(String userId) {
        Optional<MUser> mUser = mUserRepository.findById(userId);
        Optional<MUserResponse> responseOptional = mUser.map(data -> {
            MUserResponse response = new MUserResponse();
            return buildToResponse(response, data);
        });
        return responseOptional;
    }

    @Override
    public String delete(String userId) {
        MUser mUser = mUserRepository.findById(userId).orElse(null);
        mUser.setIsDeleted(true);
        mUserRepository.save(mUser);
        return "Success delete by userId : " + mUser.getUserName();
    }


    private MUserResponse buildToResponse(MUserResponse response, MUser mUser) {
        response.setUserId(mUser.getUserId());
        response.setUserName(mUser.getUserName());
        response.setEmail(mUser.getEmail());
        response.setFullname(mUser.getFullname());
        response.setUserRoleId(mUser.getUserRoleId());
        response.setIsDeleted(mUser.getIsDeleted());
        return response;
    }

    private MUser buildToEntity(MUser mUser, MUserRequest request){
        mUser.setUserName(request.getUserName());
        mUser.setEmail(request.getEmail());
        mUser.setPassword(request.getPassword());
        mUser.setUserRoleId(request.getUserRoleId());
        mUser.setFullname(request.getFullname());
        mUser.setIsDeleted(false);
        return mUser;
    }
}
