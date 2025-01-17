package com.dw.dynamic.controller;

import com.dw.dynamic.DTO.BoardDTO;
import com.dw.dynamic.model.Board;
import com.dw.dynamic.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @GetMapping("/all")
    public ResponseEntity<List<BoardDTO>> getAllBoards() {
        return new ResponseEntity<>(
                boardService.getAllBoards(),
                HttpStatus.OK);
    }
    @PostMapping("/save")
    public ResponseEntity<BoardDTO> saveBoard(@RequestBody BoardDTO boardDTO) {
        return new ResponseEntity<>(
                boardService.saveBoard(boardDTO),
                HttpStatus.CREATED);
    }
    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteBoard(@PathVariable Long id, HttpServletRequest request) {
        return new ResponseEntity<>(
                boardService.deleteBoard(id,request),
                HttpStatus.OK);
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<BoardDTO> getBoardById(@PathVariable Long id){
        return new ResponseEntity<>(
                boardService.getBoardById(id),
                HttpStatus.OK
        );
    }
    @GetMapping("/title/{title}")
    public ResponseEntity<List<BoardDTO>> getBoardsByTitle(@PathVariable String title){
        return new ResponseEntity<>(
                boardService.getBoardsByTitle(title),
                HttpStatus.OK
        );
    }

}
