package com.apriadchenko.rewardsprogram.service;

import com.apriadchenko.rewardsprogram.dto.TransactionDto;
import com.apriadchenko.rewardsprogram.entity.Transaction;

import java.util.List;

public interface TransactionsService {
    void addTransaction(TransactionDto transactionDto);

    void updateTransaction(Integer id, TransactionDto transactionDto);
    List<Transaction> getTransactionsByCustomerIdForLastThreeMonths(Integer customerId);
}
