package com.apriadchenko.rewardsprogram.service;

import com.apriadchenko.rewardsprogram.dto.response.RewardPointsResponseDto;

public interface RewardPointsService {
    RewardPointsResponseDto getRewardPointsByCustomerId(Integer customerId);
}
