package com.apriadchenko.rewardsprogram.utils;

import com.apriadchenko.rewardsprogram.dto.TransactionDto;
import com.apriadchenko.rewardsprogram.exception.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static com.apriadchenko.rewardsprogram.enums.ExceptionType.INVALID_ADD_TRANSACTION_DTO_AMOUNT;
import static com.apriadchenko.rewardsprogram.enums.ExceptionType.INVALID_ADD_TRANSACTION_DTO_CUSTOMER_ID;
import static com.apriadchenko.rewardsprogram.enums.ExceptionType.INVALID_ADD_TRANSACTION_DTO_DATE;

@Slf4j
public class RequestValidator {
    private RequestValidator() {
    }

    public static void validateAddTransactionDto(TransactionDto dto) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        if (dto.getCustomerId() == null || dto.getCustomerId() < 0) {
            log.warn("Invalid customer id: {}", dto.getCustomerId());
            throw new InvalidRequestException(INVALID_ADD_TRANSACTION_DTO_CUSTOMER_ID);
        }
        if (dto.getTransactionAmount() == null || dto.getTransactionAmount() < 0) {
            log.warn("Invalid transaction amount: {}", dto.getTransactionAmount());
            throw new InvalidRequestException(INVALID_ADD_TRANSACTION_DTO_AMOUNT);
        }
        if (dto.getDate() == null) {
            dto.setDate(LocalDate.now().format(formatter));
        } else {
            try {
                LocalDate.parse(dto.getDate(), formatter);
            } catch (DateTimeParseException e) {
                log.warn("Invalid transaction date: {}", dto.getDate());
                throw new InvalidRequestException(INVALID_ADD_TRANSACTION_DTO_DATE);
            }
        }
    }
}
