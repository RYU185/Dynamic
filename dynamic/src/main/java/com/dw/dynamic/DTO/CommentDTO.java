package com.dw.dynamic.DTO;


import com.dw.dynamic.model.Board;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentDTO {


    private Long id;

    private String boardTitle;

    private String text;

    private LocalDate addDate;

}
