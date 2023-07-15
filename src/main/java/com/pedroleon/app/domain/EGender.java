package com.pedroleon.app.domain;

public enum EGender {
    M("Male"),
    F("Female");

    private final String value;

    EGender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static EGender fromValue(String value) {
        for (EGender status : EGender.values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status value: " + value);
    }
}
