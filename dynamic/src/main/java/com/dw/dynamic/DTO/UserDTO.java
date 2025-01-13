package com.dw.dynamic.DTO;

import com.dw.dynamic.enums.Gender;
import com.dw.dynamic.model.Authority;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class UserDTO {

    private String id;

    private String companyName;

    private String name;

    private String password;

    private Gender gender;

    private String email;

    private String phoneNumber;

    private String businessNumber; // 사업자번호

    private String businessType; // 업종

    private boolean existBusinessOperator; // 기존 사업자 여부

    private Long point;

    private String role; // 권한
}
