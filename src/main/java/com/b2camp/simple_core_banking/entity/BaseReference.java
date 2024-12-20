package com.b2camp.simple_core_banking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseReference {
    @Column(name = "created_by")
    private MUser createdBy;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "update_by", length = 70)
    private MUser updateBy;

    @Column(name = "update_at")
    private Timestamp updateAt;

}
