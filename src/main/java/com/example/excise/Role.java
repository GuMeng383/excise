package com.example.excise;

public enum Role {
    CIVILIAN("平民"),
    UNDERCOVER("卧底");
    private final String description;
    Role(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
