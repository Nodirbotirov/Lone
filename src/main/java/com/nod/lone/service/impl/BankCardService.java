package com.nod.lone.service.impl;

import com.nod.lone.dto.BalanceRequest;
import com.nod.lone.dto.BankCardDto;
import com.nod.lone.model.BankCard;
import com.nod.lone.model.User;
import com.nod.lone.payload.AllApiResponse;
import com.nod.lone.repository.BankCardRepository;
import com.nod.lone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class BankCardService {

    @Autowired
    BankCardRepository bankCardRepository;

    @Autowired
    UserRepository userRepository;

    public HttpEntity<?> saveOrEdit(BankCardDto dto) {
        try {
            if (dto == null) return AllApiResponse.response(400,"bad request");
            BankCard bankCard = null;
            if (dto.getId() != null){
                Optional<BankCard> bankCardOptional = bankCardRepository.findById(dto.getId());
                if (bankCardOptional.isPresent()) bankCard = bankCardOptional.get();
            }else {
                bankCard = new BankCard();
            }

            assert bankCard != null;
            bankCard.setCardName(dto.getCardName());
            bankCard.setCardType(dto.getCardType());
            bankCard.setCvvCode(dto.getCvvCode());
            bankCard.setCardNumber(dto.getCardNumber());
            bankCard.setCreatedDate(LocalDate.now());
            bankCard.setExpireDate(dto.getExpireDate());
            bankCard.setKindOfBank(dto.getKindOfBank());
            bankCard.setOverallBalance(BigDecimal.valueOf(0.00));
            bankCard.setIsActive(true);
            if (dto.getPinCode() == null){
                bankCard.setPinCode("9999");
            }else {
                bankCard.setPinCode(dto.getPinCode());
            }
            if (dto.getUserId() == null){
                return AllApiResponse.response(400,"Karta egasi yuq. Karta egasi biriktirilishi shart!");
            }else {
                Optional<User> userOptional = userRepository.findById(dto.getUserId());
                if (userOptional.isEmpty()) {
                    return AllApiResponse.response(400,"Karta egasi yuq. Karta egasi biriktirilishi shart!");
                }else {
                    bankCard.setUser(userOptional.get());
                }
            }
            bankCardRepository.save(bankCard);
            return AllApiResponse.response(200,dto.getId()==null?"Karta muvvaffaqqiyatli ochildi!":"karta o`zgartirildi.",bankCard);
        }catch (Exception e){
            e.printStackTrace();
            return AllApiResponse.response(409,"conflict");
        }
    }

    public HttpEntity<?> watchBalance(BalanceRequest balanceRequest) {

        if (balanceRequest.getCardNumber().isEmpty()||balanceRequest.getPinCode().isEmpty()){
            return AllApiResponse.response(400, "Malumotlar to'liq emas");
        }else {
            Optional<BankCard> cardOptional = bankCardRepository.findByCardNumber(balanceRequest.getCardNumber());
            if (cardOptional.isEmpty()){
                return AllApiResponse.response(400, "Mavjud emas");
            }else {
                BankCard bankCard = cardOptional.get();
                if (bankCard.getPinCode().equals(balanceRequest.getPinCode())){
                    balanceRequest.setOverallBalance(bankCard.getOverallBalance());
                    return AllApiResponse.response(200, "pul miqdori");
                }
            }

        }
//        String cardNumber = balanceRequest.getCardNumber();
//        Optional<BankCard> card = bankCardRepository.findBankCardBycardNumber(cardNumber);
        return null;
    }
}
