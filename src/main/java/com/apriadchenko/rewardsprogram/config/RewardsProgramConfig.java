package com.apriadchenko.rewardsprogram.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("rewards.program")
@Data
@Component
public class RewardsProgramConfig {
    int firstLevel;
    int secondLevel;
    int firstLevelPoints;
    int secondLevelPoints;
}
