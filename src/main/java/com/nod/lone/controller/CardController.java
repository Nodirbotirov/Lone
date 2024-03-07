package com.nod.lone.controller;

import com.nod.lone.dto.BalanceRequest;
import com.nod.lone.dto.BankCardDto;
import com.nod.lone.service.impl.BankCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/card")
public class CardController {

    @Autowired
    BankCardService bankCardService;

    @PostMapping("/save-or-edit")
    public HttpEntity<?> addCard(@RequestBody BankCardDto dto){
        return bankCardService.saveOrEdit(dto);
    }

    @PostMapping("/view-balance")
    public HttpEntity<?> viewBalance(@RequestBody BalanceRequest balanceRequest){
        return bankCardService.watchBalance(balanceRequest);
    }


}
