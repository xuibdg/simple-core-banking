package com.b2camp.simple_core_banking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TSavingAccountResponse {
    private String savingAccountId;
    private String accountNumber;
    private String cifId;
    private BigDecimal beginBalance;
    private BigDecimal endBalance;
    private BigDecimal currentBalance;
    private String statusId;
    private boolean isDeleted;
    private Timestamp authorizationAt;
    private String authorizationBy;
    private String savingId;
    private String savingName;
    private String statusName;
    private String createdBy;
    private Timestamp createdAt;
    private String updatedBy;
    private Timestamp updatedAt;

}
