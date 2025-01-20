package com.dw.dynamic.service;

import com.dw.dynamic.DTO.BoardDTO;
import com.dw.dynamic.exception.InvalidRequestException;
import com.dw.dynamic.exception.ResourceNotFoundException;
import com.dw.dynamic.exception.UnauthorizedUserException;
import com.dw.dynamic.model.Board;
import com.dw.dynamic.model.User;
import com.dw.dynamic.repository.BoardRepository;
import com.dw.dynamic.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BoardService {
    @Autowired
    BoardRepository boardRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

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

    public BoardDTO saveBoard(BoardDTO boardDTO,HttpServletRequest request) {
        User currentUser = userService.getCurrentUser(request);
        return boardRepository.findById(boardDTO.getId())
                .map((board) -> {
                    board.setModifyDate(LocalDateTime.now());
                    return boardRepository.save(board).toDTO();
                })
                .orElseGet(() -> {
                    Board board = new Board(
                            null,
                            boardDTO.getTitle(),
                            false,
                            LocalDateTime.now(),
                            LocalDateTime.now(),
                            true,
                            currentUser,
                            null
                    );
                    return boardRepository.save(board).toDTO();
                });

    }
    public String deleteBoard(Long id, HttpServletRequest request){
        User currentUser = userService.getCurrentUser(request);
        Board board = boardRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("존재하지 않은 게시글입니다."));
        if (!board.getUser().equals(currentUser)){
            throw new UnauthorizedUserException("본인이 작성한 글에 대해서만 삭제할 수 있습니다");
        }
        board.setIsActive(false);
        boardRepository.save(board);
        return "게시판이 정상 삭제되었습니다";
    }
}
