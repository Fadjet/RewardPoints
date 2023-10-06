package com.apriadchenko.rewardsprogram.enums;

public enum ExceptionType {

    CUSTOMER_NOT_FOUND("404.001", "Customer not found."),
    TRANSACTION_NOT_FOUND("404.002", "Transaction not found."),
    INVALID_ADD_TRANSACTION_DTO_CUSTOMER_ID("400.001", "Invalid add transaction request: empty customer id"),
    INVALID_ADD_TRANSACTION_DTO_AMOUNT("400.002", "Invalid add transaction request: empty amount"),
    INVALID_ADD_TRANSACTION_DTO_DATE("400.003", "Invalid date format, correct format is yyyy-mm-dd");


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
