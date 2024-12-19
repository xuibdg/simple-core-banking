package com.b2camp.simple_core_banking.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name = "m_cif", schema = "public")
public class MCif extends BaseReference {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "cif_id", nullable = false)
    private String cifId;

    @Column(name = "customer_name", nullable = false, length = 100)
    private String customerName;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "address", nullable = false, length = 255)
    private String address;

    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "id_number", nullable = false, length = 50)
    private String idNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_number_type", referencedColumnName = "type_id")
    private RNumberType rNumberType;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id", referencedColumnName = "status_id")
    private RStatus rStatus;

    @Column(name = "authorization_at")
    private Timestamp authorizationAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authorization_by", referencedColumnName = "user_id")
    private MUser mUserAuthorizationBy;


    @ManyToOne(fetch = FetchType.LAZY) // Relasi ke MUser
    @JoinColumn(name = "created_by", referencedColumnName = "user_id")
    private MUser createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by", referencedColumnName = "user_id")
    private MUser updatedBy;


    // No-argument constructor
    public MCif() {
    }

    // All-argument constructor
    public MCif(String cifId, String customerName, LocalDate dateOfBirth, String address, String phoneNumber,
                String email, String idNumber, RNumberType rNumberType, boolean isDeleted, RStatus rStatus,
                Timestamp authorizationAt, MUser mUserAuthorizationBy, MUser createdBy, MUser updatedBy) {
        this.cifId = cifId;
        this.customerName = customerName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.idNumber = idNumber;
        this.rNumberType = rNumberType;
        this.isDeleted = isDeleted;
        this.rStatus = rStatus;
        this.authorizationAt = authorizationAt;
        this.mUserAuthorizationBy = mUserAuthorizationBy;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    public MUser getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(MUser updatedBy) {
        this.updatedBy = updatedBy;
    }

    public MUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(MUser createdBy) {
        this.createdBy = createdBy;
    }

    public String getCifId() {

        return cifId;
    }

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

    public RNumberType getrNumberType() {
        return rNumberType;
    }

    public void setrNumberType(RNumberType rNumberType) {
        this.rNumberType = rNumberType;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }


    public void setrStatus(RStatus rStatus) {
        this.rStatus = rStatus;
    }

    public RStatus getrStatus() {
        return rStatus;
    }

    public Timestamp getAuthorization_at() {
        return authorizationAt;
    }

    public void setAuthorization_at(Timestamp authorization_at) {
        this.authorizationAt = authorization_at;
    }

    public MUser getmUserAuthorizationBy() {
        return mUserAuthorizationBy;
    }

    public void setmUserAuthorizationBy(MUser mUserAuthorizationBy) {
        this.mUserAuthorizationBy = mUserAuthorizationBy;
    }

    public Timestamp getAuthorizationAt() {
        return authorizationAt;
    }

    public void setAuthorizationAt(Timestamp authorizationAt) {
        this.authorizationAt = authorizationAt;
    }


}
