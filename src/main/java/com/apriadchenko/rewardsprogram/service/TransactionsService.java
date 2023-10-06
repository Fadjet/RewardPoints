package com.apriadchenko.rewardsprogram.service;

import com.apriadchenko.rewardsprogram.dto.AddTransactionDto;
import com.apriadchenko.rewardsprogram.entity.Transaction;

import java.util.List;

public interface TransactionsService {
    void addTransaction(AddTransactionDto addTransactionDto);
    List<Transaction> getTransactionsByCustomerIdForLastThreeMonths(Integer customerId);
}
