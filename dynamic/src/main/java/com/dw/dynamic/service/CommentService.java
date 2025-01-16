package com.dw.dynamic.service;

import com.dw.dynamic.DTO.CommentDTO;
import com.dw.dynamic.exception.InvalidRequestException;
import com.dw.dynamic.exception.ResourceNotFoundException;
import com.dw.dynamic.model.Comment;
import com.dw.dynamic.repository.BoardRepository;
import com.dw.dynamic.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserService userService;

    @Autowired
    BoardRepository boardRepository;
//
//    public List<CommentDTO> getAllComments(){
//        try {
//            return commentRepository.findAll().stream().map(Comment::toDTO).toList();
//        }catch (InvalidRequestException e){
//            throw  new InvalidRequestException("정상적인 요청이 아닙니다");
//        }
//    }
//
//    public List<CommentDTO> getCommentByBoardId(Long boardId){
//        try {
//            List<Comment> commentList = commentRepository.findByBoard_fkId(boardId);
//            return commentList.stream().map(Comment::toDTO).toList();
//        }catch (ResourceNotFoundException e){
//            throw new ResourceNotFoundException("존재하지 않은 게시판ID입니다");
//        }
//    }
//
//    public List<CommentDTO> getCommentByBoardTitle(String boardTitle){
//        try {
//            List<Comment> commentList= commentRepository.findByBoard_fkTitle("%"+boardTitle+"%");
//            return commentList.stream().map(Comment::toDTO).toList();
//        }catch (ResourceNotFoundException e){
//            throw  new ResourceNotFoundException("존재하지 않은 게시판 제목입니다");
//        }
//    }
}
