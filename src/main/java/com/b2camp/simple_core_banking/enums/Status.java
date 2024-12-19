package com.b2camp.simple_core_banking.enums;

public enum Status {

    ACTIVE("ACTIVE", "ACTIVE"),
    PENDING("PENDING", "PENDING"),
    INACTIVE("INACTIVE", "INACTIVE"),
    //IN_PROGRESS("IN_PROGRESS", "IN PROGRESS"),
    //REJECT("REJECT", "REJECT")
    ;

    private final String key;
    private final String value;

    Status(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }
    public String getValue() {
        return value;
    }
}
