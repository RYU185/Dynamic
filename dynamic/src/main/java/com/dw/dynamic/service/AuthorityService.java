package com.dw.dynamic.service;

import com.dw.dynamic.exception.InvalidRequestException;
import com.dw.dynamic.exception.PermissionDeniedException;
import com.dw.dynamic.exception.ResourceNotFoundException;
import com.dw.dynamic.model.Authority;
import com.dw.dynamic.model.User;
import com.dw.dynamic.repository.AuthorityRepository;
import com.dw.dynamic.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityService {
    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    public List<Authority> getAllAuthoritys(HttpServletRequest request){
        User currentuUser = userService.getCurrentUser(request);
        if (!currentuUser.getAuthority().getAuthorityName().equals("ADMIN")){
            throw new PermissionDeniedException("권한이 없습니다");
        }
        try{
            return authorityRepository.findAll();
        } catch (InvalidRequestException e){
            throw new InvalidRequestException("정상적인 요청이 아닙니다.");
        }
    }

    public Authority getAuthorityById(String id,HttpServletRequest request){
        User currentuUser = userService.getCurrentUser(request);
        if (!currentuUser.getAuthority().getAuthorityName().equals("ADMIN")){
            throw new PermissionDeniedException("권한이 없습니다");
        }
        return authorityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 ID입니다"));
    }

    public List<Authority> getAuthoritysByName(String authorityName,HttpServletRequest request){
        User currentuUser = userService.getCurrentUser(request);
        if (!currentuUser.getAuthority().getAuthorityName().equals("ADMIN")){
            throw new PermissionDeniedException("권한이 없습니다");
        }
        try {
            return authorityRepository.findByAuthorityNameLike("%"+authorityName+"%");
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("존재하지 않은 권한명입니다.");
        }


    }
}
