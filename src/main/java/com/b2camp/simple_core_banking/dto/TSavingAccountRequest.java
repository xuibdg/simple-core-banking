package com.b2camp.simple_core_banking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TSavingAccountRequest {
    private String accountNumber;
    private String cifId;
    private String savingId;
    private BigDecimal beginBalance;
    private BigDecimal endBalance;
    private BigDecimal currentBalance;

}