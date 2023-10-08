package com.apriadchenko.rewardsprogram.service.impl;

import com.apriadchenko.rewardsprogram.dto.TransactionDto;
import com.apriadchenko.rewardsprogram.entity.Transaction;
import com.apriadchenko.rewardsprogram.enums.ExceptionType;
import com.apriadchenko.rewardsprogram.exception.NotFoundException;
import com.apriadchenko.rewardsprogram.repository.TransactionRepository;
import com.apriadchenko.rewardsprogram.service.CustomerService;
import com.apriadchenko.rewardsprogram.service.TransactionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.apriadchenko.rewardsprogram.utils.RequestValidator.validateTransactionDto;


@Service
@RequiredArgsConstructor
public class TransactionsServiceImpl implements TransactionsService {
    private final TransactionRepository transactionRepository;
    private final CustomerService customerService;


    @Override
    @Transactional
    public void addTransaction(TransactionDto transactionDto) {
        validateTransactionDto(transactionDto);
        Transaction transaction = dtoToEntity(transactionDto);
        transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public void updateTransaction(Integer id, TransactionDto transactionDto) {
        validateTransactionDto(transactionDto);
        transactionRepository.findById(id).orElseThrow(() -> new NotFoundException(ExceptionType.TRANSACTION_NOT_FOUND));
        Transaction updatedTransaction = dtoToEntity(transactionDto);
        updatedTransaction.setId(id);
        transactionRepository.save(updatedTransaction);
    }

    @Override
    public List<Transaction> getTransactionsByCustomerIdForLastThreeMonths(Integer customerId) {
        return transactionRepository.findAllByCustomerIdAndCreatedDateGreaterThan(customerId, LocalDate.now().minusMonths(3));
    }


    private Transaction dtoToEntity(TransactionDto transactionDto) {
        return Transaction.builder()
                .amount(transactionDto.getTransactionAmount())
                .customer(customerService.getCustomerById(transactionDto.getCustomerId()))
                .createdDate(LocalDate.parse(transactionDto.getDate()))
                .build();
    }
}
