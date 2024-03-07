package com.nod.lone.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceRequest {

    private Long id;
    private String pinCode;
    private String cardNumber;
    private BigDecimal overallBalance;
}
