package com.dw.dynamic.service;

import com.dw.dynamic.exception.InvalidRequestException;
import com.dw.dynamic.exception.PermissionDeniedException;
import com.dw.dynamic.exception.ResourceNotFoundException;
import com.dw.dynamic.model.FormationData;
import com.dw.dynamic.model.User;
import com.dw.dynamic.repository.FormationDataRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormationDataService {

    @Autowired
    FormationDataRepository formationDataRepository;

    @Autowired
    UserService userService;

    public List<FormationData> getAllFormationData(){
        try {
            return formationDataRepository.findAll();
        }catch (InvalidRequestException e){
            throw new InvalidRequestException("정상적인 요청이 아닙니다");
        }
    }
    public FormationData getFormationDataById(Long id){
        return formationDataRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("존재하지 않는 ID입니다"));
    }

    public List<FormationData> getFormationDataByTitle(String title){
        try {
            return formationDataRepository.findByTitleLike("%"+title+"%");
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("존재하지 않는 제목입니다");
        }
    }

    public FormationData saveFormationData(FormationData formationData, HttpServletRequest request){
        User currentuUser = userService.getCurrentUser(request);
        if (!currentuUser.getAuthority().getAuthorityName().equals("ADMIN")){
            throw new PermissionDeniedException("권한이 없습니다");
        }
        try {
            return formationDataRepository.save(formationData);
        }catch (InvalidRequestException e){
            throw new InvalidRequestException("제목을 작성해주세요");
        }
    }
    public String deleteFormationData(Long id, HttpServletRequest request){
        User currentuUser = userService.getCurrentUser(request);
        if (!currentuUser.getAuthority().getAuthorityName().equals("ADMIN")){
            throw new PermissionDeniedException("권한이 없습니다");
        }
        if (formationDataRepository.findById(id).isEmpty()){
            throw new ResourceNotFoundException("존재하지 않는 ID입니다");
        }
        formationDataRepository.deleteById(id);
        return "ID : " + id +"가 정상 삭제되었습니다";
    }
}
