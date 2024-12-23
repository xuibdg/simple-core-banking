package com.b2camp.simple_core_banking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MCifResponse {

    private String cifId;
    private String customerName;
    private LocalDate dateOfBirth;
    private String address;
    private String phoneNumber;
    private String email;
    private String idNumber;
    private String idNumberType;
    private boolean isDeleted;
    private Timestamp createdAt;
    private Timestamp upDateAt;
    private String createdBy;
    private String upDateBy;
    private Timestamp authorizationAt;
    private String authorizationBy;
    private String statusId;
    private String statusName;
}
