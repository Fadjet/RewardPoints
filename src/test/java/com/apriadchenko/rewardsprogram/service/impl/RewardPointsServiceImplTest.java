package com.apriadchenko.rewardsprogram.service.impl;

import com.apriadchenko.rewardsprogram.enums.ExceptionType;
import com.apriadchenko.rewardsprogram.exception.NotFoundException;
import com.apriadchenko.rewardsprogram.service.CustomerService;
import com.apriadchenko.rewardsprogram.service.TransactionsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RewardPointsServiceImplTest {
    @Mock private CustomerService customerService;
    @Mock private TransactionsService transactionsService;

    @InjectMocks RewardPointsServiceImpl target;

    @Test
    void getRewardPointsByCustomerId_customerNotFound() {
        doThrow(new NotFoundException(ExceptionType.CUSTOMER_NOT_FOUND)).when(customerService).getCustomerById(0);
        target.getRewardPointsByCustomerId(0);
        verify(transactionsService, times(0)).getTransactionsByCustomerIdForLastThreeMonths(0);
    }

}