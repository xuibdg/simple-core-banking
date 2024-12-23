package com.b2camp.simple_core_banking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "r_status" , schema = "public")
public class RStatus {

    @Id
    @Column(name = "status_id",nullable = false)
    private String statusId;

    @Column(name = "status_code",nullable = false)
    private String statusCode;

    @Column(name = "status_name",nullable = false)
    private String statusName;
}
