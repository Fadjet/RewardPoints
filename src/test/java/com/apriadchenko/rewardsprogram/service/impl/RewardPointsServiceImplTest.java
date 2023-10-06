package com.apriadchenko.rewardsprogram.service.impl;

import com.apriadchenko.rewardsprogram.config.RewardsProgramConfig;
import com.apriadchenko.rewardsprogram.dto.response.RewardPointsResponseDto;
import com.apriadchenko.rewardsprogram.entity.Customer;
import com.apriadchenko.rewardsprogram.entity.Transaction;
import com.apriadchenko.rewardsprogram.service.CustomerService;
import com.apriadchenko.rewardsprogram.service.TransactionsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RewardPointsServiceImplTest {
    @Mock
    private CustomerService customerService;
    @Mock
    private TransactionsService transactionsService;
    @Mock
    private RewardsProgramConfig rewardsProgramConfig;

    @InjectMocks
    RewardPointsServiceImpl target;

    @Test
    void getRewardPointsByCustomerId_NoTransactionsFound() {
        when(transactionsService.getTransactionsByCustomerIdForLastThreeMonths(0)).thenReturn(new ArrayList<>());
        RewardPointsResponseDto responseDto = target.getRewardPointsByCustomerId(0);

        verify(transactionsService, atMostOnce()).getTransactionsByCustomerIdForLastThreeMonths(0);
        verify(customerService, atMostOnce()).getCustomerById(0);
        assertNotNull(responseDto);
        assertNotNull(responseDto.getResult());
        assertTrue(responseDto.getRewardPointsPerMonth().isEmpty());
        assertEquals(0, responseDto.getTotalRewardPoints());
        assertTrue(responseDto.getResult().getSuccess());
        assertEquals(200, responseDto.getResult().getReturnCode());
    }

    @Test
    void getRewardPointsByCustomerId_TransactionsFound() {
        Transaction transaction1 = Transaction.builder()
                .customer(Customer.builder()
                        .id(0)
                        .build())
                .amount(101.1)
                .createdDate(LocalDate.now().minusMonths(2))
                .build();
        Transaction transaction2 = Transaction.builder()
                .customer(Customer.builder()
                        .id(0)
                        .build())
                .amount(52.0)
                .createdDate(LocalDate.now().minusMonths(1))
                .build();
        Transaction transaction3 = Transaction.builder()
                .customer(Customer.builder()
                        .id(0)
                        .build())
                .amount(15.0)
                .createdDate(LocalDate.now())
                .build();
        when(transactionsService.getTransactionsByCustomerIdForLastThreeMonths(0)).thenReturn(List.of(transaction1, transaction2, transaction3));
        when(rewardsProgramConfig.getFirstLevel()).thenReturn(50);
        when(rewardsProgramConfig.getSecondLevel()).thenReturn(100);
        when(rewardsProgramConfig.getFirstLevelPoints()).thenReturn(1);
        when(rewardsProgramConfig.getSecondLevelPoints()).thenReturn(2);

        RewardPointsResponseDto responseDto = target.getRewardPointsByCustomerId(0);

        verify(transactionsService, atMostOnce()).getTransactionsByCustomerIdForLastThreeMonths(0);
        verify(customerService, atMostOnce()).getCustomerById(0);

        assertNotNull(responseDto);
        assertNotNull(responseDto.getResult());
        assertTrue(responseDto.getResult().getSuccess());
        assertEquals(200, responseDto.getResult().getReturnCode());

        assertFalse(responseDto.getRewardPointsPerMonth().isEmpty());
        assertEquals(3, responseDto.getRewardPointsPerMonth().size());
        assertNotNull(responseDto.getRewardPointsPerMonth().get(LocalDateTime.now().minusMonths(2).getMonth().toString()));
        assertNotNull(responseDto.getRewardPointsPerMonth().get(LocalDateTime.now().minusMonths(1).getMonth().toString()));
        assertNotNull(responseDto.getRewardPointsPerMonth().get(LocalDateTime.now().getMonth().toString()));
        assertEquals(52, responseDto.getRewardPointsPerMonth().get(LocalDateTime.now().minusMonths(2).getMonth().toString()));
        assertEquals(2, responseDto.getRewardPointsPerMonth().get(LocalDateTime.now().minusMonths(1).getMonth().toString()));
        assertEquals(0, responseDto.getRewardPointsPerMonth().get(LocalDateTime.now().getMonth().toString()));
        assertEquals(54, responseDto.getTotalRewardPoints());

    }

}