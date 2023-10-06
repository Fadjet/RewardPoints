package com.apriadchenko.rewardsprogram.service.impl;

import com.apriadchenko.rewardsprogram.config.RewardsProgramConfig;
import com.apriadchenko.rewardsprogram.dto.AddTransactionDto;
import com.apriadchenko.rewardsprogram.entity.Transaction;
import com.apriadchenko.rewardsprogram.repository.TransactionRepository;
import com.apriadchenko.rewardsprogram.service.CustomerService;
import com.apriadchenko.rewardsprogram.service.TransactionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TransactionsServiceImpl implements TransactionsService {
    private final TransactionRepository transactionRepository;
    private final CustomerService customerService;
    private final RewardsProgramConfig rewardsProgramConfig;

    @Override
    @Transactional
    public void addTransaction(AddTransactionDto addTransactionDto) {
        Transaction transaction = Transaction.builder()
                .amount(addTransactionDto.getTransactionAmount())
                .customer(customerService.getCustomerById(addTransactionDto.getCustomerId()))
                .rewardPoints(calculateRewardPoints(addTransactionDto.getTransactionAmount()))
                .build();
        transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getTransactionsByCustomerIdForLastThreeMonths(Integer customerId) {
        return transactionRepository.findAllByCustomerIdAndCreatedDateGreaterThan(customerId, LocalDateTime.now().minusMonths(3));
    }

    private Integer calculateRewardPoints(Double amount) {
        int firstRewardPointsLevel = rewardsProgramConfig.getFirstLevel();
        int secondRewardPointsLevel = rewardsProgramConfig.getSecondLevel();
        int firstLevelPoints = rewardsProgramConfig.getFirstLevelBonus();
        int secondLevelPoints = rewardsProgramConfig.getSecondLevelBonus();
        int intAmount = amount.intValue();
        if (intAmount < firstRewardPointsLevel) {
            return 0;
        } else if (intAmount <= secondRewardPointsLevel) {
            return (intAmount - firstRewardPointsLevel) * firstLevelPoints;
        } else {
            return firstRewardPointsLevel * firstLevelPoints + (intAmount - secondRewardPointsLevel) * secondLevelPoints;
        }
    }
}
