package com.dw.dynamic.model;


import com.dw.dynamic.DTO.CommentDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board_fk;

    @Column(name = "text")
    private String text;

    @Column(name = "add_date")
    private LocalDate addDate;

    @Column(name="is_active")
    private Boolean isActive = true;

    public CommentDTO toDTO(){
        return new CommentDTO(
                this.id,
                this.board_fk.getTitle(),
                this.text,
                this.addDate
        );
    }
}
