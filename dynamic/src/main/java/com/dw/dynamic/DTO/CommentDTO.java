package com.dw.dynamic.DTO;


import com.dw.dynamic.model.Board;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentDTO {

    private Long id;

    private BoardDTO board;

    private String text;

    private LocalDateTime addDate;
}
