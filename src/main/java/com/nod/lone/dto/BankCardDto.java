package com.nod.lone.dto;

import com.nod.lone.model.enums.CardType;
import com.nod.lone.model.enums.KindOfBank;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class BankCardDto {

    private Long id;

    private String cardName;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    private Long cvvCode;

    private String cardNumber;

    private String pinCode;

    private LocalDate expireDate;

    @Enumerated(EnumType.STRING)
    private KindOfBank kindOfBank;

    private Long userId;
}
