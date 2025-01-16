package com.dw.dynamic.repository;

import com.dw.dynamic.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    public List<Comment> findByBoard_fkId(Long boardId);

    public List<Comment> findByBoard_fkTitle(String title);
}
}
