package com.b2camp.simple_core_banking.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private LocalDateTime createdAt;
    private LocalDateTime upDateAt;
    private String createdBy;
    private String upDateBy;
    private LocalDateTime authorizationAt;
    private String authorizationBy;
    private String statusId;
    private String statusName;

    public String getCifId() { return cifId; }

    public void setCifId(String cifId) {
        this.cifId = cifId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getIdNumberType() {
        return idNumberType;
    }

    public void setIdNumberType(String idNumberType) {
        this.idNumberType = idNumberType;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpDateAt() {
        return upDateAt;
    }

    public void setUpDateAt(LocalDateTime upDateAt) {
        this.upDateAt = upDateAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpDateBy() {
        return upDateBy;
    }

    public void setUpDateBy(String upDateBy) {
        this.upDateBy = upDateBy;
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

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
