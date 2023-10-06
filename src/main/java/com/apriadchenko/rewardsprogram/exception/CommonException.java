package com.apriadchenko.rewardsprogram.exception;

import com.apriadchenko.rewardsprogram.enums.ExceptionType;

public class CommonException extends RuntimeException{
    private final ExceptionType exceptionType;
    public CommonException(ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    public String getErrorCode() {
        return this.exceptionType.getErrorCode();
    }

    @Override
    public String getMessage() {
        return this.exceptionType.getErrorMessage();
    }
}
