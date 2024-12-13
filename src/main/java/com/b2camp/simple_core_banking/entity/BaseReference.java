package com.b2camp.simple_core_banking.entity;

import jakarta.persistence.Column;

import java.sql.Timestamp;

public abstract class BaseReference {
    @Column(name = "created_by")
    private MUser createdBy;

    @Column(name = "created_at", columnDefinition = "timestamp(6)")
    private Timestamp createdAt;

    @Column(name = "update_by", length = 70)
    private MUser updateBy;

    @Column(name = "update_at", columnDefinition = "timestamp(6)")
    private Timestamp updateAt;

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
