package com.pedroleon.app.domain;

public enum EAlignment {
    B("Bad"),
    G("Good");

    private final String value;

    EAlignment(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static EAlignment fromValue(String value) {
        for (EAlignment status : EAlignment.values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status value: " + value);
    }
}
