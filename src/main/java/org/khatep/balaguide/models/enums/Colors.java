package org.khatep.balaguide.models.enums;

public enum Colors {
    RESET("\u001B[0m"),
    BLUE("\u001B[34m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    CYAN("\u001B[36m"),
    PURPLE("\u001B[35m");

    private final String code;
    Colors(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}