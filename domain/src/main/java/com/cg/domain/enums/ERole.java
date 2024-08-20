package com.cg.domain.enums;

public enum  ERole {
    ADMIN("ADMIN"),

    EMPLOYEE("EMPLOYEE"),
    CUSTOMER("CUSTOMER");

    private final String value;
    ERole(String value) {
        this.value = value;
    }
    public String getValue() {
        return this.value;
    }
}
