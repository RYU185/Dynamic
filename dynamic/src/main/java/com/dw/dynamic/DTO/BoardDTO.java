package com.dw.dynamic.DTO;

import com.dw.dynamic.model.Comment;
import com.dw.dynamic.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BoardDTO {

    private Long id;

    private String title;

    private boolean answer; // 관리자 답변 여부

    private LocalDateTime addDate; // 작성일

    private String userName; // 유저(단방향)

}
