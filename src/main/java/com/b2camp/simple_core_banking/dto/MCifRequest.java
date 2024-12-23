package com.b2camp.simple_core_banking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MCifRequest {
    private String customerName;
    private LocalDate dateOfBirth;
    private String address;
    private String phoneNumber;
    private String email;
    private String idNumber;
    private String idNumberType;
}
