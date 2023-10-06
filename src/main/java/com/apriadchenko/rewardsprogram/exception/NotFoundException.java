package com.apriadchenko.rewardsprogram.exception;

import com.apriadchenko.rewardsprogram.enums.ExceptionType;

public class NotFoundException extends CommonException{
    public NotFoundException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
