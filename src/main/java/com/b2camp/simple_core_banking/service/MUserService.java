package com.b2camp.simple_core_banking.service;

import com.b2camp.simple_core_banking.dto.LoginRequest;
import com.b2camp.simple_core_banking.dto.MUserRequest;
import com.b2camp.simple_core_banking.dto.MUserResponse;

import java.util.List;
import java.util.Optional;


public interface MUserService {
    String login(LoginRequest request);

    MUserResponse createUser(MUserRequest request);

    MUserResponse updateUser(String userId,MUserRequest request);

    List<MUserResponse> read(String userName);

    Optional<MUserResponse> findById(String userId);

    String delete(String userId);
}
