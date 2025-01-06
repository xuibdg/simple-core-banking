package com.b2camp.simple_core_banking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MUserResponse {

    private String userId;
    private String email;
    private String userName;
    private String userRoleId;
    private String fullName;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updateBy;
    private LocalDateTime updateAt;
    private boolean isDeleted;
}
