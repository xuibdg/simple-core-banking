package com.b2camp.simple_core_banking.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MUserRequest {
    private String email;
    private String userName;
    private String password;
    private String userRoleId;
    private String fullName;
    private String idNumber;
    private String idNumberType;
    private BigDecimal price;

}
