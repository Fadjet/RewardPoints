package com.apriadchenko.rewardsprogram.exception;

import com.apriadchenko.rewardsprogram.enums.ExceptionType;

public class InvalidRequestException extends CommonException {
    public InvalidRequestException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
