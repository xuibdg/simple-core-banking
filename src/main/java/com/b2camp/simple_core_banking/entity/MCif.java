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
    @Column(name = "cif_id", nullable = false, length = 20)
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

    @ManyToOne
    @JoinColumn(name = "id_number_type", referencedColumnName = "type_id")
    private RNumberType rNumberType;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "status_id")
    private RStatus rStatus;

    @Column(name = "authorization_at", nullable = false)
    private Timestamp authorization_at;

    @ManyToOne
    @JoinColumn(name = "authorization_by", referencedColumnName = "user_id")
    private MUser mUserAuthorizationBy;


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
        return authorization_at;
    }

    public void setAuthorization_at(Timestamp authorization_at) {
        this.authorization_at = authorization_at;
    }

    public MUser getmUserAuthorizationBy() {
        return mUserAuthorizationBy;
    }

    public void setmUserAuthorizationBy(MUser mUserAuthorizationBy) {
        this.mUserAuthorizationBy = mUserAuthorizationBy;
    }
}
