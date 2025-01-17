package com.dw.dynamic.model;


import com.dw.dynamic.DTO.CommentDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private Board board;

    @Column(name = "text")
    private String text;

    @Column(name = "add_date")
    private LocalDateTime addDate;

    @Column(name="is_active")
    private Boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "user_name")
    private User user;

    public CommentDTO toDTO(){
        return new CommentDTO(
                this.id,
                this.board.getId(),
                this.board.getTitle(),
                this.user.getUserName(),
                this.text,
                this.addDate
        );
    }
}
