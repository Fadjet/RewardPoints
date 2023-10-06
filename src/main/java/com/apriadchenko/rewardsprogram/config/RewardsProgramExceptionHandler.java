package com.apriadchenko.rewardsprogram.config;

import com.apriadchenko.rewardsprogram.dto.response.BaseResponse;
import com.apriadchenko.rewardsprogram.dto.response.Result;
import com.apriadchenko.rewardsprogram.exception.InvalidRequestException;
import com.apriadchenko.rewardsprogram.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class RewardsProgramExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BaseResponse handle(NotFoundException exception) {
        log.warn(exception.getMessage());
        return buildBaseResponse(exception.getMessage(), exception.getErrorCode(), HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler({InvalidRequestException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse handle(InvalidRequestException exception) {
        log.warn(exception.getMessage());
        return buildBaseResponse(exception.getMessage(), exception.getErrorCode(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler({Exception.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse handle(Exception exception) {
        log.error(exception.getMessage(), exception);
        return buildBaseResponse(exception.getMessage(), "500.001", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    private BaseResponse buildBaseResponse(String message, String errorCode, int httpStatus) {
        return BaseResponse.builder()
                .result(Result.builder()
                        .errorCode(errorCode)
                        .errorMessage(message)
                        .returnCode(httpStatus)
                        .success(false)
                        .build())
                .build();
    }
}
