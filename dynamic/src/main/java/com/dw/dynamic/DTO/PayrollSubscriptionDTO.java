package com.dw.dynamic.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PayrollSubscriptionDTO {

    private String title; // 구독권 제목(ex. 3개월 구독권)

    private LocalDate startDate; // 구독 시작일

    private LocalDate expireDate; // 구독 만료일

    private String productId;
}
