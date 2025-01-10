package com.dw.dynamic.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Rating {

    VERY_BAD(1, "매우 불만족"),
    BAD(2, "불만족"),
    AVERAGE(3, "보통"),
    GOOD(4, "만족"),
    EXCELLENT(5, "매우 만족");


    private final int ratingValue;
    private final String ratingDescription;
}
