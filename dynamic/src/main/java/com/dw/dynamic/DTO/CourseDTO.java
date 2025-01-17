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
public class CourseDTO {

    private String title;

    private LocalDate addDate; // 강의 업로드 날짜

    private String description; // 상세 설명

    private String productId;

}
