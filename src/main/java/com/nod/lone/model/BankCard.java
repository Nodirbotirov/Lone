package com.nod.lone.model;

import com.nod.lone.model.enums.KindOfBank;
import com.nod.lone.model.enums.CardType;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class BankCard{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cardName;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @Column(unique = true)
    private Long cvvCode;

    @Column(unique = true)
    private String cardNumber;

    private LocalDate createdDate;

    private LocalDate expireDate;

    private String pinCode;

    @Enumerated(EnumType.STRING)
    private KindOfBank kindOfBank;

    private BigDecimal overallBalance;

    private Boolean isActive;

    @ManyToOne
    private User user;

    public BankCard(Long id, String cardName, CardType cardType, String cardNumber, LocalDate createdDate, LocalDate expireDate, KindOfBank kindOfBank, BigDecimal overallBalance, Boolean isActive) {
        this.id = id;
        this.cardName = cardName;
        this.cardType = cardType;
        this.cardNumber = cardNumber;
        this.createdDate = createdDate;
        this.expireDate = expireDate;
        this.kindOfBank = kindOfBank;
        this.overallBalance = overallBalance;
        this.isActive = isActive;
    }

    public BankCard() {

    }
}
