package com.apriadchenko.rewardsprogram.service.impl;

import com.apriadchenko.rewardsprogram.config.RewardsProgramConfig;
import com.apriadchenko.rewardsprogram.dto.response.Result;
import com.apriadchenko.rewardsprogram.dto.response.RewardPointsResponseDto;
import com.apriadchenko.rewardsprogram.entity.Transaction;
import com.apriadchenko.rewardsprogram.service.CustomerService;
import com.apriadchenko.rewardsprogram.service.RewardPointsService;
import com.apriadchenko.rewardsprogram.service.TransactionsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
public class RewardPointsServiceImpl implements RewardPointsService {
    private final CustomerService customerService;
    private final TransactionsService transactionsService;
    private final RewardsProgramConfig rewardsProgramConfig;

    @Override
    public RewardPointsResponseDto getRewardPointsByCustomerId(Integer customerId) {
        customerService.getCustomerById(customerId);
        List<Transaction> transactions = transactionsService.getTransactionsByCustomerIdForLastThreeMonths(customerId);
        log.info("Found {} transactions for customer id {}", transactions.size(), customerId);
        return RewardPointsResponseDto.builder()
                .rewardPointsPerMonth(getRewardPointsByMonth(transactions))
                .totalRewardPoints(transactions.stream()
                        .map(t -> calculateRewardPoints(t.getAmount()))
                        .mapToInt(Integer::intValue).sum())
                .result(Result.builder().success(true).returnCode(200).build())
                .build();
    }

    private Map<String, Integer> getRewardPointsByMonth(List<Transaction> transactions) {
        Map<String, Integer> rewardPointsMap = new HashMap<>();
        transactions.forEach(t -> {
            String month = t.getCreatedDate().getMonth().toString();
            Integer rewardPoints = calculateRewardPoints(t.getAmount());
            rewardPointsMap.merge(month, rewardPoints, Integer::sum);
        });
        return rewardPointsMap;
    }

    private Integer calculateRewardPoints(Double amount) {
        int firstRewardPointsLevel = rewardsProgramConfig.getFirstLevel();
        int secondRewardPointsLevel = rewardsProgramConfig.getSecondLevel();
        int firstLevelPoints = rewardsProgramConfig.getFirstLevelPoints();
        int secondLevelPoints = rewardsProgramConfig.getSecondLevelPoints();
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
