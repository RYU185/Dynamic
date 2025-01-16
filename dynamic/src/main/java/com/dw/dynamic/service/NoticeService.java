package com.dw.dynamic.service;

import com.dw.dynamic.exception.InvalidRequestException;
import com.dw.dynamic.exception.PermissionDeniedException;
import com.dw.dynamic.exception.ResourceNotFoundException;
import com.dw.dynamic.model.Notice;
import com.dw.dynamic.model.User;
import com.dw.dynamic.repository.NoticeRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class NoticeService {
    @Autowired
    NoticeRepository noticeRepository;

    @Autowired
    UserService userService;

    public List<Notice> getAllNotices(){
        try {
            return noticeRepository.findAll();
        }catch (InvalidRequestException e){
            throw new InvalidRequestException("정상적인 요청이 아닙니다");
        }
    }

    public Notice getNoticeById(Long id){
        return noticeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 ID입니다"));

    }

    public List<Notice> getNoticesByTitle(String title){
        try {
            return noticeRepository.findByNoticeTitle("%"+title+"%");
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("존재하지 않는 제목입니다");
        }
    }

    public String deleteNotice(Long id, HttpServletRequest request){
        User currentuUser = userService.getCurrentUser(request);
        if (!currentuUser.getAuthority().getAuthorityName().equals("ADMIN")){
            throw new PermissionDeniedException("권한이 없습니다");
        }
        if (noticeRepository.findById(id).isEmpty()){
            throw new ResourceNotFoundException("존재하지 않는 ID입니다");
        }
        noticeRepository.deleteById(id);
        return "ID : " + id +"가 정상 삭제되었습니다";
    }

    public Notice saveNotice(Notice notice,HttpServletRequest request){
        User currentuUser = userService.getCurrentUser(request);
        if (!currentuUser.getAuthority().getAuthorityName().equals("ADMIN")){
            throw new PermissionDeniedException("권한이 없습니다");
        }
        try {
            if (noticeRepository.findById(notice.getNoticeId()).isPresent()){
                notice.setModifiedDate(LocalDate.now());
            }else {
                notice.setAddDate(LocalDate.now());
                notice.setModifiedDate(LocalDate.now());
            }
        }catch (InvalidRequestException e){
            throw new InvalidRequestException("제목과 본문 모두 작성해주세요");
        }
        return noticeRepository.save(notice);
    }

}
