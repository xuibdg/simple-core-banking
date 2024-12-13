package com.b2camp.simple_core_banking.entity;

import jakarta.persistence.Column;

import java.time.LocalDateTime;

public abstract class BaseReference {
    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at", columnDefinition = "timestamp(6)")
    private LocalDateTime createdAt;

    @Column(name = "update_by", length = 70)
    private String updateBy;

    @Column(name = "update_at", columnDefinition = "timestamp(6)")
    private LocalDateTime updateAt;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }
}
