package com.dw.dynamic.service;

import com.dw.dynamic.exception.InvalidRequestException;
import com.dw.dynamic.exception.PermissionDeniedException;
import com.dw.dynamic.exception.ResourceNotFoundException;
import com.dw.dynamic.model.Guide;
import com.dw.dynamic.model.User;
import com.dw.dynamic.repository.GuideRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuideService {
    @Autowired
    GuideRepository guideRepository;

    @Autowired
    UserService userService;

    public List<Guide> getAllGuides(){
        try {
            return guideRepository.findAll();
        }catch (InvalidRequestException e){
            throw new InvalidRequestException("정상적이 요청이 아닙니다");
        }
    }
    public Guide getGuideById(Long id){
        return guideRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 ID입니다"));
    }

    public List<Guide>  getGuidesByTitle(String title){
        try {
            return guideRepository.findByTitleLike("%"+title+"%");
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("존재하지 않는 제목입니다");
        }
    }

    public Guide saveGuide(Guide guide, HttpServletRequest request){
        User currentuUser = userService.getCurrentUser(request);
        if (!currentuUser.getAuthority().getAuthorityName().equals("ADMIN")){
            throw new PermissionDeniedException("권한이 없습니다");
        }
        try {
            return guideRepository.save(guide);
        }catch (InvalidRequestException e){
            throw new InvalidRequestException("제목과 본문 모두 작성해주세요");
        }
    }

    public String deleteGuide(Long id, HttpServletRequest request){
        User currentUser = userService.getCurrentUser(request);
        if (!currentUser.getAuthority().getAuthorityName().equals("ADMIN")){
            throw new PermissionDeniedException("권한이 없습니다");
        }
        if (guideRepository.findById(id).isEmpty()){
            throw new ResourceNotFoundException("존재하지 않는 ID입니다");
        }
        guideRepository.deleteById(id);
        return "ID : " + id +"가 정상 삭제되었습니다";
    }
}
