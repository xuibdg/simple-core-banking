package com.b2camp.simple_core_banking.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class TSavingAccountRequest {
    private String accountNumber;
    private String cifId;
    private String savingId;
    private BigDecimal beginBalance;
    private BigDecimal endBalance;
    private BigDecimal currentBalance;
    private boolean isDeleted;
    private Timestamp authorizationAt;


    public BigDecimal getBeginBalance() {
        return beginBalance;
    }

    public void setBeginBalance(BigDecimal beginBalance) {
        this.beginBalance = beginBalance;
    }

    public BigDecimal getEndBalance() {
        return endBalance;
    }

    public void setEndBalance(BigDecimal endBalance) {
        this.endBalance = endBalance;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Timestamp getAuthorizationAt() {
        return authorizationAt;
    }

    public void setAuthorizationAt(Timestamp authorizationAt) {
        this.authorizationAt = authorizationAt;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCifId() {
        return cifId;
    }

    public void setCifId(String cifId) {
        this.cifId = cifId;
    }

    public String getSavingId() {
        return savingId;
    }

    public void setSavingId(String savingId) {
        this.savingId = savingId;
    }
}
