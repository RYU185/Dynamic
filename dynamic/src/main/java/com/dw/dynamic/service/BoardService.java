package com.dw.dynamic.service;

import com.dw.dynamic.DTO.BoardDTO;
import com.dw.dynamic.exception.InvalidRequestException;
import com.dw.dynamic.exception.PermissionDeniedException;
import com.dw.dynamic.exception.ResourceNotFoundException;
import com.dw.dynamic.exception.UnauthorizedUserException;
import com.dw.dynamic.model.Board;
import com.dw.dynamic.model.Guide;
import com.dw.dynamic.model.User;
import com.dw.dynamic.repository.BoardRepository;
import com.dw.dynamic.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BoardService {
    @Autowired
    BoardRepository boardRepository;

    @Autowired
    UserService userService;


    public List<BoardDTO> getAllBoards() {
        try {
            return boardRepository.findAll().stream().map(Board::toDTO).toList();
        } catch (InvalidRequestException e) {
            throw new InvalidRequestException("정상적인 요청이 아닙니다");
        }

    }

    public BoardDTO getBoardById(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 ID입니다"));

        return board.toDTO();
    }

    public List<BoardDTO> getBoardsByTitle(String title) {
        try {
            List<Board> board = boardRepository.findByTitleLike("%" + title + "%");
            return board.stream().map(Board::toDTO).toList();
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("존재하지 않는 제목입니다");
        }
    }

    public BoardDTO updateBoard(BoardDTO boardDTO,HttpServletRequest request) {
        User currentUser = userService.getCurrentUser(request);

        Board board = boardRepository.findById(boardDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("존재하지 않은  ID입니다"));
        board.setModifyDate(LocalDate.now());
        board.setTitle(boardDTO.getTitle());
        return boardRepository.save(board).toDTO();

    }
    public BoardDTO saveBoard(BoardDTO boardDTO, HttpServletRequest request){
        User currentUser = userService.getCurrentUser(request);

        try {
           Board board = new Board(
                    null,
                   boardDTO.getTitle(),
                   false,
                   LocalDate.now(),
                   LocalDate.now(),
                   true,
                   currentUser,
                   null
            );
            return boardRepository.save(board).toDTO();
        }catch (InvalidRequestException e){
            throw new InvalidRequestException("본문을 입력해주세요");
        }
    }


    public String deleteBoard(Long id, HttpServletRequest request){
        User currentUser = userService.getCurrentUser(request);
        Board board = boardRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("존재하지 않은 게시글입니다."));
        if (board.getUser().equals(currentUser)){
            board.setIsActive(false);
            boardRepository.save(board);
            return "게시판이 정상 삭제되었습니다";
        }
        if (currentUser.getAuthority().getAuthorityName().equals("ADMIN")){
            board.setIsActive(false);
            boardRepository.save(board);
            return "관리자가 게시글을 삭제했습니다";
        }
        throw new UnauthorizedUserException("삭제할 권한이 없습니다");
    }
}
