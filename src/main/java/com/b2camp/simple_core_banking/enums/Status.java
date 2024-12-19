package com.b2camp.simple_core_banking.enums;

public enum Status {

    ACTIVE("1", "ACTIVE"),
    PENDING("2", "PENDING"),
    INACTIVE("3", "INACTIVE"),
    //IN_PROGRESS("4", "IN PROGRESS"),
    //REJECT("5", "REJECT")
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
