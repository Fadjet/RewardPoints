package com.apriadchenko.rewardsprogram.service.impl;

import com.apriadchenko.rewardsprogram.dto.TransactionDto;
import com.apriadchenko.rewardsprogram.entity.Customer;
import com.apriadchenko.rewardsprogram.entity.Transaction;
import com.apriadchenko.rewardsprogram.enums.ExceptionType;
import com.apriadchenko.rewardsprogram.exception.InvalidRequestException;
import com.apriadchenko.rewardsprogram.repository.TransactionRepository;
import com.apriadchenko.rewardsprogram.service.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionsServiceImplTest {
    @Mock
    private
    TransactionRepository transactionRepository;
    @Mock
    private CustomerService customerService;

    @InjectMocks
    TransactionsServiceImpl target;

    @Test
    void addTransaction_EmptyCustomerId() {
        TransactionDto dto = TransactionDto.builder().customerId(null).transactionAmount(12.1).build();
        try {
            target.addTransaction(dto);
            Assertions.fail("No exception has been thrown");
        } catch (InvalidRequestException e) {
            Assertions.assertEquals(ExceptionType.INVALID_ADD_TRANSACTION_DTO_CUSTOMER_ID.getErrorCode(), e.getErrorCode());
            Assertions.assertEquals(ExceptionType.INVALID_ADD_TRANSACTION_DTO_CUSTOMER_ID.getErrorMessage(), e.getMessage());
        }
    }

    @Test
    void addTransaction_EmptyAmount() {
        TransactionDto dto = TransactionDto.builder().customerId(0).transactionAmount(null).build();
        try {
            target.addTransaction(dto);
            Assertions.fail("No exception has been thrown");
        } catch (InvalidRequestException e) {
            Assertions.assertEquals(ExceptionType.INVALID_ADD_TRANSACTION_DTO_AMOUNT.getErrorCode(), e.getErrorCode());
            Assertions.assertEquals(ExceptionType.INVALID_ADD_TRANSACTION_DTO_AMOUNT.getErrorMessage(), e.getMessage());
        }
    }

    @Test
    void addTransaction() {


        TransactionDto dto = TransactionDto.builder().customerId(0).transactionAmount(101.0).build();
        Customer customer = mock(Customer.class);
        ArgumentCaptor<Transaction> argument = ArgumentCaptor.forClass(Transaction.class);
        when(customerService.getCustomerById(0)).thenReturn(customer);
        target.addTransaction(dto);

        verify(transactionRepository, atMostOnce()).save(argument.capture());
        assertEquals(dto.getTransactionAmount(), argument.getValue().getAmount());
    }

    @Test
    void getTransactionsByCustomerIdForLastThreeMonths() {
        target.getTransactionsByCustomerIdForLastThreeMonths(0);
        verify(transactionRepository, atMostOnce()).findAllByCustomerIdAndCreatedDateGreaterThan(0, LocalDate.now().minusMonths(3));
    }
}