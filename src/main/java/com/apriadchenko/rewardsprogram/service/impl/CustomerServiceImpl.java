package com.apriadchenko.rewardsprogram.service.impl;

import com.apriadchenko.rewardsprogram.entity.Customer;
import com.apriadchenko.rewardsprogram.exception.NotFoundException;
import com.apriadchenko.rewardsprogram.repository.CustomerRepository;
import com.apriadchenko.rewardsprogram.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.apriadchenko.rewardsprogram.enums.ExceptionType.CUSTOMER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;


    @Override
    public Customer getCustomerById(Integer customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> new NotFoundException(CUSTOMER_NOT_FOUND));
    }
}
