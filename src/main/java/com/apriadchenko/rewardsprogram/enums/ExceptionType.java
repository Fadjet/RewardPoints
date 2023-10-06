package com.apriadchenko.rewardsprogram.enums;

public enum ExceptionType {

    CUSTOMER_NOT_FOUND("404.001", "Customer not found.");

    private final String errorCode;
    private final String errorMessage;

    ExceptionType(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
