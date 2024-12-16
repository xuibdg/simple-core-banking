package com.b2camp.simple_core_banking.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TSavingAccountResponse {
    private String savingAccountId;
    private String accountNumber;
    private String cifId;
    private BigDecimal beginBalance;
    private BigDecimal endBalance;
    private BigDecimal currentBalance;
    private String statusId;
    private boolean isDeleted;
    private LocalDateTime authorizationAt;
    private String authorizationBy;
    private String savingId;


    public String getSavingAccountId() {
        return savingAccountId;
    }

    public void setSavingAccountId(String savingAccountId) {
        this.savingAccountId = savingAccountId;
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

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public LocalDateTime getAuthorizationAt() {
        return authorizationAt;
    }

    public void setAuthorizationAt(LocalDateTime authorizationAt) {
        this.authorizationAt = authorizationAt;
    }

    public String getAuthorizationBy() {
        return authorizationBy;
    }

    public void setAuthorizationBy(String authorizationBy) {
        this.authorizationBy = authorizationBy;
    }

    public String getSavingId() {
        return savingId;
    }

    public void setSavingId(String savingId) {
        this.savingId = savingId;
    }

}
