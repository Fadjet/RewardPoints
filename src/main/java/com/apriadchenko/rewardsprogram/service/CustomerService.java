package com.apriadchenko.rewardsprogram.service;

import com.apriadchenko.rewardsprogram.entity.Customer;

import java.util.Optional;

public interface CustomerService {
    Customer getCustomerById(Integer customerId);
}
