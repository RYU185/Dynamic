package com.dw.dynamic.service;

import com.dw.dynamic.DTO.CommentDTO;
import com.dw.dynamic.exception.InvalidRequestException;
import com.dw.dynamic.exception.ResourceNotFoundException;
import com.dw.dynamic.exception.UnauthorizedUserException;
import com.dw.dynamic.model.Board;
import com.dw.dynamic.model.Comment;
import com.dw.dynamic.model.User;
import com.dw.dynamic.repository.BoardRepository;
import com.dw.dynamic.repository.CommentRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserService userService;

    @Autowired
    BoardRepository boardRepository;

    public List<CommentDTO> getAllComments(){
        try {
            return commentRepository.findAll().stream().map(Comment::toDTO).toList();
        }catch (InvalidRequestException e){
            throw  new InvalidRequestException("정상적인 요청이 아닙니다");
        }
    }

    public List<CommentDTO> getCommentByBoardId(Long boardId){
        try {
            List<Comment> commentList = commentRepository.findByBoardId(boardId);
            return commentList.stream().map(Comment::toDTO).toList();
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("존재하지 않은 게시판ID입니다");
        }
    }

    public List<CommentDTO> getCommentByBoardTitle(String boardTitle){
        try {
            List<Comment> commentList= commentRepository.findByBoardTitleLike("%"+boardTitle+"%");
            return commentList.stream().map(Comment::toDTO).toList();
        }catch (ResourceNotFoundException e){
            throw  new ResourceNotFoundException("존재하지 않은 게시판 제목입니다");
        }
    }
    public CommentDTO saveCommentByBoardId(CommentDTO commentDTO, HttpServletRequest request){
        User currentUser = userService.getCurrentUser(request);
        if (commentDTO.getText()==null){
            throw new InvalidRequestException("내용을 입력해주세요");
        }
        if (currentUser.getAuthority().getAuthorityName().equals("USER")){
            Comment comment = new Comment(
                    null,
                    boardRepository.findById(commentDTO.getBoardId()).orElseThrow(()->new ResourceNotFoundException("존재하지 않은 게시판ID입니다")),
                    commentDTO.getText(),
                    LocalDate.now(),
                    true,
                    currentUser
            );
            return commentRepository.save(comment).toDTO();
        } else if (currentUser.getAuthority().getAuthorityName().equals("ADMIN")) {
            Board board = boardRepository.findById(commentDTO.getBoardId()).orElseThrow(()->new ResourceNotFoundException("존재하지 않은 게시판ID입니다"));
            Comment comment = new Comment(
                    null,
                    board,
                    commentDTO.getText(),
                    LocalDate.now(),
                    true,
                    currentUser
            );
            board.setAnswer(true);
            return commentRepository.save(comment).toDTO();
        }else {
            throw new UnauthorizedUserException("비회원은 댓글 작성이 불가능합니다. 회원가입을 해주세요");
        }
    }

    public String deleteComment(Long id, HttpServletRequest request){
        User currentUser = userService.getCurrentUser(request);
        Comment comment = commentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("존재하지 않은 댓글입니다."));
        if (comment.getUser().equals(currentUser)){
            comment.setIsActive(false);
            commentRepository.save(comment);
            return "댓글이 정상 삭제되었습니다.";
        }
        if (currentUser.getAuthority().getAuthorityName().equals("ADMIN")){
            comment.setIsActive(false);
            commentRepository.save(comment);
            return "관리자가 댓글을 삭제하였습니다.";
        }
        throw new UnauthorizedUserException("댓글을 삭제할 권한이 없습니다.");
    }
}
