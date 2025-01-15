package com.dw.dynamic.model;

import com.dw.dynamic.DTO.UserDTO;
import com.dw.dynamic.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name="user_name")
    private String userName;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "real_name", nullable = false)
    private String realName;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name = "gender",nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender; // ENUM 수정 필요

    @Column(name="email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number",nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "business_number")
    private String businessNumber; // 사업자번호

    @Column(name = "business_type")
    private String businessType; // 업종

    @Column(name = "exist_business_operator", nullable = false)
    private Boolean existBusinessOperator= false; // 기존 사업자 여부

    @Column(name = "point")
    private Long point;

    @ManyToOne
    @JoinColumn(name = "user_authority")
    private Authority authority; // 권한

    public UserDTO toDTO(){
        return new UserDTO(
                this.userName,
                this.companyName,
                this.realName,
                null,
                this.gender.name(),
                this.email,
                this.phoneNumber,
                this.businessNumber,
                this.businessType,
                this.existBusinessOperator,
                this.point,
                this.authority.getAuthorityName()
        );
    }
}
