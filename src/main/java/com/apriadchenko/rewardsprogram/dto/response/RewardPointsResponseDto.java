package com.apriadchenko.rewardsprogram.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class RewardPointsResponseDto extends BaseResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = -4709467264340239869L;
    private Map<String, Integer> rewardPointsPerMonth;
    private Integer totalRewardPoints;
}
