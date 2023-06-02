package com.kodlamaio.commonpackage.utils.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    private String cardHolder;
    private String cardNumber;
    private int cardExpirationYear;
    private int cardExpirationMonth;
    private String cardCvv;
}