package com.b2camp.simple_core_banking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "r_number_type",schema = "public")
public class RNumberType {
    @Id
    @Column(name = "type_id",nullable = false)
    private String typeId;

    @Column(name = "type_code",nullable = false)
    private String typeCode;

    @Column(name = "type_name",nullable = false)
    private String typeName;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }


}
