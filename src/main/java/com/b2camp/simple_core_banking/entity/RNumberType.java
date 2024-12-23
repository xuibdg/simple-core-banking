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
@Table(name = "r_number_type",schema = "public")
public class RNumberType {
    @Id
    @Column(name = "type_id",nullable = false)
    private String typeId;

    @Column(name = "type_code",nullable = false)
    private String typeCode;

    @Column(name = "type_name",nullable = false)
    private String typeName;

}
