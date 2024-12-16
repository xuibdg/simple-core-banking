package com.b2camp.simple_core_banking.entity;

import jakarta.persistence.Column;

import java.sql.Timestamp;

public abstract class BaseReference {
    @Column(name = "created_by")
    private MUser createdBy;


    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "update_by", length = 70)
    private MUser updateBy;


    @Column(name = "update_at")
    private Timestamp updateAt;

    // No-argument constructor
    public BaseReference() {
    }

    // All-argument constructor
    public BaseReference(MUser createdBy, Timestamp createdAt, MUser updateBy, Timestamp updateAt) {
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updateBy = updateBy;
        this.updateAt = updateAt;

    }

    public MUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(MUser createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public MUser getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(MUser updateBy) {
        this.updateBy = updateBy;
    }

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }


}
