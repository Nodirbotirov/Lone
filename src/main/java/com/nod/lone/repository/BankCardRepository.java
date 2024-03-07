package com.nod.lone.repository;

import com.nod.lone.model.BankCard;
import com.nod.lone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BankCardRepository extends JpaRepository<BankCard, Long> {

    List<BankCard> findAllByUserOrderByCreatedDate(User user);

    Optional<BankCard> findBankCardByCardNumber(String cardNumber);

}
