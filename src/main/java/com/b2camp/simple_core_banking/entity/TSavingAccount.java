package com.b2camp.simple_core_banking.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_saving_account", schema = "public")
public class TSavingAccount extends BaseReference {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "saving_account_id", nullable = false)
    private String savingAccountId;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cif_id", referencedColumnName = "cif_id")
    private MCif cifId;

    @Column(name = "begin_balance")
    private BigDecimal beginBalance;

    @Column(name = "end_balance")
    private BigDecimal endBalance;

    @Column(name = "current_balance")
    private BigDecimal currentBalance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id", referencedColumnName = "status_id")
    private RStatus rStatus;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @Column(name = "authorization_at")
    private LocalDateTime authorizationAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "authorization_by", referencedColumnName = "user_id")
    private MUser mUserAuthorizationBy;

    @Column(name = "saving_id", nullable = false)
    private String savingId;

    @Column(name = "updated_at")
    private Timestamp updateAt;


    // No-argument constructor
    public TSavingAccount() {
    }

    // All-argument constructor
    public TSavingAccount(String savingAccountId, String accountNumber, MCif cifId, BigDecimal beginBalance,
                          BigDecimal endBalance, BigDecimal currentBalance, RStatus rStatus, boolean isDeleted,
                          LocalDateTime authorizationAt, MUser mUserAuthorizationBy, String savingId) {
        this.savingAccountId = savingAccountId;
        this.accountNumber = accountNumber;
        this.cifId = cifId;
        this.beginBalance = beginBalance;
        this.endBalance = endBalance;
        this.currentBalance = currentBalance;
        this.rStatus = rStatus;
        this.isDeleted = isDeleted;
        this.authorizationAt = authorizationAt;
        this.mUserAuthorizationBy = mUserAuthorizationBy;
        this.savingId = savingId;
    }

    //Getter and Setter
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

    public MCif getCifId() {
        return cifId;
    }

    public void setCifId(MCif cifId) {
        this.cifId = cifId;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public void setSaving(String savingId) {
        this.savingId = savingId;
    }

    public RStatus getrStatus() {
        return rStatus;
    }

    public void setrStatus(RStatus rStatus) {
        this.rStatus = rStatus;
    }

    public MUser getmUserAuthorizationBy() {
        return mUserAuthorizationBy;
    }

    public void setmUserAuthorizationBy(MUser mUserAuthorizationBy) {
        this.mUserAuthorizationBy = mUserAuthorizationBy;
    }

    public void setBeginBalance(BigDecimal beginBalance) {
        this.beginBalance = beginBalance;
    }

    public void setEndBalance(BigDecimal endBalance) {
        this.endBalance = endBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public LocalDateTime getAuthorizationAt() {
        return authorizationAt;
    }

    public void setAuthorizationAt(LocalDateTime authorizationAt) {
        this.authorizationAt = authorizationAt;
    }


    public String getSavingId() {
        return savingId;
    }

    public void setSavingId(String savingId) {
        this.savingId = savingId;
    }

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }

    public BigDecimal getBeginBalance(String s) {
        return beginBalance;
    }

    public BigDecimal getEndBalance(String a) {
        return endBalance;
    }

    public BigDecimal getCurrentBalance(String s) {
        return currentBalance;
    }
}
