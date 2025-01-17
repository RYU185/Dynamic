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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
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
            return noticeRepository.findByNoticeTitleLike("%"+title+"%");
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
        User currentUser = userService.getCurrentUser(request);
        if (!currentUser.getAuthority().getAuthorityName().equals("ADMIN")){
            throw new PermissionDeniedException("권한이 없습니다");
        }
        if (notice.getNoticeTitle()==null || notice.getText()==null){
            throw new InvalidRequestException("제목과 본문 모두 작성해주세요");
        }
        return noticeRepository.findById(notice.getNoticeId())
        .map((notice1) -> {
                    notice1.setModifiedDate(LocalDate.now());
                    return noticeRepository.save(notice1);
                }).orElseGet(()->{
                    Notice notice2=new Notice(
                            null,
                           notice.getNoticeTitle(),
                            notice.getText(),
                            LocalDate.now(),
                            LocalDate.now()
                    );
                    return noticeRepository.save(notice2);
                });
    }

}
