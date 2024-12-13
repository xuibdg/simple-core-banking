package com.b2camp.simple_core_banking.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "m_saving", schema = "public")
public class MSaving {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "saving_id", nullable = false)
    private String savingId;

    @Column(name = "saving_cod")
    private String savingCod;

    @Column(name = "saving_name")
    private String savingName;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "denom")
    private Integer denom;

    @Column(name = "minimal_balance")
    private Integer minimalBalance;

    @Column(name = "tax")
    private Integer tax;


    //getter and setter

    public void setSavingId(String savingId) {
        this.savingId = savingId;
    }

    public void setSavingCod(String savingCod) {
        this.savingCod = savingCod;
    }

    public void setSavingName(String savingName) {
        this.savingName = savingName;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public void setDenom(Integer denom) {
        this.denom = denom;
    }

    public void setMinimalBalance(Integer minimalBalance) {
        this.minimalBalance = minimalBalance;
    }

    public void setTax(Integer tax) {
        this.tax = tax;
    }

    public String getSavingId() {
        return savingId;
    }

    public String getSavingCod() {
        return savingCod;
    }

    public String getSavingName() {
        return savingName;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public Integer isDenom() {
        return denom;
    }

    public Integer isMinimalBalance() {
        return minimalBalance;
    }

    public Integer isTax() {
        return tax;
    }
}
