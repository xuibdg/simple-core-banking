package com.b2camp.simple_core_banking.dto;

public class TSavingAccountRequest {
    private String accountNumber;
    private String cifId;
    private String savingId;


    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    //getter setter untuk cifID
    public String getCifId() {
        return cifId = cifId;
    }

    public void setCifId(String cifId) {
        this.cifId = cifId;
    }

    //getter setter untuk savingID
    public String getSavingId() {
        return savingId;
    }

    public void setSavingId(String savingId) {
        this.savingId = savingId;
    }

}