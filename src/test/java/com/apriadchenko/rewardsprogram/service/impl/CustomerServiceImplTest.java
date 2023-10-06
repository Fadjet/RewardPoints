package com.apriadchenko.rewardsprogram.service.impl;

import com.apriadchenko.rewardsprogram.entity.Customer;
import com.apriadchenko.rewardsprogram.enums.ExceptionType;
import com.apriadchenko.rewardsprogram.exception.NotFoundException;
import com.apriadchenko.rewardsprogram.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl target;

    @Test
    void getCustomerById_CustomerNotFound() {
        when(customerRepository.findById(0)).thenReturn(Optional.empty());
        try {
            target.getCustomerById(0);
            Assertions.fail("Exception has not been thrown");
        } catch (NotFoundException e) {
            assertEquals(e.getErrorCode(), ExceptionType.CUSTOMER_NOT_FOUND.getErrorCode());
            assertEquals(e.getMessage(), ExceptionType.CUSTOMER_NOT_FOUND.getErrorMessage());
        }
    }

    @Test
    void getCustomerById_CustomerFound() {
        when(customerRepository.findById(0)).thenReturn(Optional.of(mock(Customer.class)));
        Customer result = target.getCustomerById(0);
        assertNotNull(result);
    }
}