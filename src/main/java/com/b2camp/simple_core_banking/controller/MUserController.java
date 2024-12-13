package com.b2camp.simple_core_banking.controller;


import com.b2camp.simple_core_banking.dto.MUserRequest;
import com.b2camp.simple_core_banking.dto.MUserResponse;
import com.b2camp.simple_core_banking.service.MUserService;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class MUserController {

    private MUserService mUserService;

    MUserController(MUserService mUserService){
        this.mUserService = mUserService;
    }

    @PostMapping
    public MUserResponse createUser(@RequestBody MUserRequest request) {
        return mUserService.createUser(request);
    }

    @PutMapping("/{userId}")
    public MUserResponse createUser(@PathVariable String userId,
                                    @RequestBody MUserRequest request) {
        return mUserService.updateUser(userId, request);
    }

    @GetMapping
    public List<MUserResponse> read(@Param("userName") String userName) {
        return mUserService.read(userName);
    }

    @GetMapping("/{userId}")
    public Optional<MUserResponse> findById(@PathVariable String userId) {
        return mUserService.findById(userId);
    }

    @DeleteMapping("/{userId}")
    public String delete(@PathVariable String userId) {
        return mUserService.delete(userId);
    }
}
