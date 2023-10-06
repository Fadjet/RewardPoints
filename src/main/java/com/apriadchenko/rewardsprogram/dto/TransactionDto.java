package com.apriadchenko.rewardsprogram.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
public class TransactionDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 4623853086763791147L;
    @NotNull
    Integer customerId;
    @NotNull
    Double transactionAmount;
    String date;
}
