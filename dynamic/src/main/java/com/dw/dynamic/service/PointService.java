package com.dw.dynamic.service;

import com.dw.dynamic.DTO.PointDTO;
import com.dw.dynamic.exception.ResourceNotFoundException;
import com.dw.dynamic.model.Point;
import com.dw.dynamic.model.User;
import com.dw.dynamic.repository.PointRepository;
import com.dw.dynamic.repository.PurchaseHistoryRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointService {

    @Autowired
    PointRepository pointRepository;

    @Autowired
    PurchaseHistoryRepository purchaseHistoryRepository;
    @Autowired
    UserService userService;

    public List<PointDTO> getAllPoints(HttpServletRequest request){
        User currentUser = userService.getCurrentUser(request);
        try {
            return pointRepository.findByUser(currentUser).stream().map(Point::toDTO).toList();
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("조회되는 포인트가 없습니다");
        }
    }

    public PointDTO getPointById(Long id, HttpServletRequest request){
        User currentUser = userService.getCurrentUser(request);
     return   pointRepository.findById(id).map(Point::toDTO).orElseThrow(() -> new ResourceNotFoundException("존재하지 않은 직원ID입니다."));
    }

    public long calculatePoint(double amount){
        return (long)(amount * 0.05);
    }
}
