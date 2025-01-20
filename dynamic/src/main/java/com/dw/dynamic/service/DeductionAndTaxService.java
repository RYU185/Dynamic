package com.dw.dynamic.service;

import com.dw.dynamic.exception.InvalidRequestException;
import com.dw.dynamic.exception.PermissionDeniedException;
import com.dw.dynamic.exception.ResourceNotFoundException;
import com.dw.dynamic.model.DeductionAndTax;
import com.dw.dynamic.model.User;
import com.dw.dynamic.repository.DeductionAndTaxRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeductionAndTaxService {

    @Autowired
    DeductionAndTaxRepository deductionAndTaxRepository;

    @Autowired
    UserService userService;

    public List<DeductionAndTax> getAllDeductionAndTaxs(){
        try {
            return deductionAndTaxRepository.findAll();
        }catch (InvalidRequestException e){
            throw new InvalidRequestException("정상적인 요청이 아닙니다");
        }
    }
    public DeductionAndTax getDeductionAndTaxById(String id){
        return deductionAndTaxRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("존재하지 않은 공제항목입니다"));
    }

    public List<DeductionAndTax>  getDeductionAndTaxByName(String name){
        try {
            return deductionAndTaxRepository.findByNameLike("%"+name+"%");
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("존재하지 않은 공제항목입니다");
        }
    }

    public DeductionAndTax saveDeductionAndTax(DeductionAndTax deductionAndTax, HttpServletRequest request){
        User currentUser = userService.getCurrentUser(request);
        if (deductionAndTax.getName() ==null ||deductionAndTax.getAmount() ==null){
            throw new InvalidRequestException("제목과 금액은 필수 입력 사항입니다");
        }
        if (!currentUser.getAuthority().getAuthorityName().equals("ADMIN")){
            throw new PermissionDeniedException("권한이 없습니다");
        }else {
            return deductionAndTaxRepository.save(deductionAndTax);
        }
    }

    public String deleteDeductionAndTax(String name, HttpServletRequest request){
        User currentUser = userService.getCurrentUser(request);
        if (!currentUser.getAuthority().getAuthorityName().equals("ADMIN")){
            throw new PermissionDeniedException("권한이 없습니다");
        }else {
            try {
                deductionAndTaxRepository.deleteById(name);
            }catch (InvalidRequestException e){
                throw new InvalidRequestException("존재하지 않은 공제항목입니다(명칭을 확인하여 다시 입력해주세요)");
            }
        }
        return "공제항목 : "+name+"이 정상 삭제되었습니다.";
    }
}
