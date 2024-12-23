package com.b2camp.simple_core_banking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "user_id")
    private MUser createdBy;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "updated_by", referencedColumnName = "user_id")
    private MUser updatedBy;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

}
