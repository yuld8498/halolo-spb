package com.cg.domain.enums;

public enum EPostType {
    POST("POST"),
    PROFILE("PROFILE"),
    COVER("COVER");

    private final String value;

    EPostType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }}
