package com.dw.dynamic.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {

    MALE("남"),
    FEMALE("여");

    private final String gender;
}
