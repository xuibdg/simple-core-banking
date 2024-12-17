package com.b2camp.simple_core_banking.dto;

public class TSavingAccountRequest {
    private String accountNumber;
    private String cif;
    private String savingId;


    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    //getter setter untuk cifID
    public String getCifId() {
        return cif;
    }

    public void setCifId(String cif) {
        this.cif= cif;
    }

    //getter setter untuk savingID
    public String getSavingId() {
        return savingId;
    }

    public void setSavingId(String savingId) {
        this.savingId = savingId;
    }

}