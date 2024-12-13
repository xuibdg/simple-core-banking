package com.b2camp.simple_core_banking.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
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

    @ManyToOne
    @JoinColumn(name = "cif_id", referencedColumnName = "cif_id")
    private MCif mCif;

    @Column(name = "begin_balance", nullable = false)
    private BigDecimal beginBalance;

    @Column(name = "end_balance", nullable = false)
    private BigDecimal endBalance ;

    @Column(name = "current_balance", nullable = false)
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

    @ManyToOne
    @JoinColumn (name = "saving_id", referencedColumnName = "saving_id")
    private MSaving mSaving;

    // No-argument constructor
    public TSavingAccount() {
    }

    // All-argument constructor
    public TSavingAccount(String savingAccountId, String accountNumber, MCif mCifId, BigDecimal beginBalance,
                          BigDecimal endBalance, BigDecimal currentBalance, RStatus rStatus, boolean isDeleted,
                          LocalDateTime authorizationAt, MUser mUserAuthorizationBy, MSaving savingId) {
        this.savingAccountId = savingAccountId;
        this.accountNumber = accountNumber;
        this.mCif = mCifId;
        this.beginBalance = beginBalance;
        this.endBalance = endBalance;
        this.currentBalance = currentBalance;
        this.rStatus = rStatus;
        this.isDeleted = isDeleted;
        this.authorizationAt = authorizationAt;
        this.mUserAuthorizationBy = mUserAuthorizationBy;
        this.mSaving = savingId;
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

    public MCif getMCif() {
        return mCif;
    }

    public void setMCif(MCif mcif) {
        this.mCif = mcif;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
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

    public MSaving getMSaving() {
        return mSaving;
    }

    public void setMSaving(MSaving mSaving) {
        this.mSaving = mSaving;
    }


}
