package com.dw.dynamic.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentMethod {
    CREDIT_CARD("카드"),
    ACCOUNT_TRANSFER("계좌이체");

    private final String paymentMethod;
}
