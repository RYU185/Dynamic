package com.dw.dynamic.repository;

import com.dw.dynamic.DTO.BoardDTO;
import com.dw.dynamic.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> {
    public List<Board> findByTitleLike(String title);

}
