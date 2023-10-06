package com.apriadchenko.rewardsprogram.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class AddTransactionDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 4623853086763791147L;
    Integer customerId;
    Double transactionAmount;
}
