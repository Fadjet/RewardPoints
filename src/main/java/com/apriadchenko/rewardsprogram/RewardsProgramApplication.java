package com.apriadchenko.rewardsprogram;

import com.apriadchenko.rewardsprogram.config.RewardsProgramConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = {RewardsProgramConfig.class})
public class RewardsProgramApplication {

    public static void main(String[] args) {
        SpringApplication.run(RewardsProgramApplication.class, args);
    }

}
