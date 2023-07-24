package com.pedroleon.app.domain;

public enum ERole {
    USER("USER"),
    ADMIN("ADMIN");

    private final String value;

    ERole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ERole fromValue(String value) {
        for (ERole status : ERole.values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status value: " + value);
    }
}
