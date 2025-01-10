package com.dw.dynamic.model;

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
    @Column(name="user_id")
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number",nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "business_number")
    private String businessNumber;

    @Column(name = "business_type")
    private String businessType;

    @Column(name = "exist_business_operator")
    private boolean existBusinessOperator;

    @Column(name = "point")
    private int point;



    @OneToOne(mappedBy = "user")
    private Cart cart; // 장바구니

    @ManyToOne
    @JoinColumn(name = "user_authority")
    private Authority authority; // 권한

    @OneToMany(mappedBy = "guide")
    private List<Guide> guide; // 가이드

    @OneToMany(mappedBy = "notice")
    private List<Notice> notice; // 공지사항

    @OneToMany(mappedBy = "review")
    private List<Review> review;  // 리뷰

    @OneToMany(mappedBy = "board")
    private List<Board> board;  // 게시판

    @OneToMany(mappedBy = "employee")
    private List<Employee> employee; // 직원 관리
}
