package com.apriadchenko.rewardsprogram.controller;

import com.apriadchenko.rewardsprogram.dto.response.RewardPointsResponseDto;
import com.apriadchenko.rewardsprogram.service.RewardPointsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RewardPointsController extends BaseController{

    private final RewardPointsService rewardPointsService;

    @GetMapping(value = "/reward-points/by-customer-id/{customerId}")
    public ResponseEntity<RewardPointsResponseDto> getRewardPointsByCustomerId(@PathVariable Integer customerId) {
        return ResponseEntity.ok(rewardPointsService.getRewardPointsByCustomerId(customerId));
    }
}
