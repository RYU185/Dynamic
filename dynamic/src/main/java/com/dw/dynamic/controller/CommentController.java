package com.dw.dynamic.controller;

import com.dw.dynamic.DTO.CommentDTO;
import com.dw.dynamic.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @GetMapping("/all")
    public ResponseEntity<List<CommentDTO>> getAllComment(){
        return new ResponseEntity<>(
                commentService.getAllComments(),
                HttpStatus.OK
        );
    }
    @GetMapping("/board-title/{boardTitle}")
    public ResponseEntity<List<CommentDTO>> getCommentByBoardTitle(@PathVariable String boardTitle){
        return new ResponseEntity<>(
                commentService.getCommentByBoardTitle(boardTitle),
                HttpStatus.OK
        );
    }
    @GetMapping("/board-id/{boardId}")
    public ResponseEntity<List<CommentDTO>> getCommentByBoardId(@PathVariable Long boardId){
        return new ResponseEntity<>(
                commentService.getCommentByBoardId(boardId),
                HttpStatus.OK
        );
    }

    @PostMapping("/save")
    public ResponseEntity<CommentDTO> saveCommentByBoardId(@RequestBody CommentDTO commentDTO, HttpServletRequest request){
        return new ResponseEntity<>(
                commentService.saveCommentByBoardId(commentDTO,request),
                HttpStatus.OK
        );
    }
    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id, HttpServletRequest request){
        return new ResponseEntity<>(
                commentService.deleteComment(id, request),
                HttpStatus.OK
        );
    }
}
