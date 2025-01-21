package com.dw.dynamic.service;

import com.dw.dynamic.DTO.ProductDTO;
import com.dw.dynamic.DTO.PurchaseHistoryDTO;
import com.dw.dynamic.exception.ResourceNotFoundException;
import com.dw.dynamic.model.*;
import com.dw.dynamic.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseHistoryService {
    @Autowired
    PurchaseHistoryRepository purchaseHistoryRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    PayrollSubscriptionRepository payrollSubscriptionRepository;
    @Autowired
    ProductRepository productRepository;

    public List<PurchaseHistoryDTO> getAllPurchaseHistorys(HttpServletRequest request){
        User currentUser = userService.getCurrentUser(request);
        if (currentUser == null){
            throw new IllegalArgumentException("올바르지 않은 접근입니다");
        }
        return purchaseHistoryRepository.findByUser(currentUser).stream().map(PurchaseHistory::toDTO).toList();
    }

    public PurchaseHistoryDTO getPurchaseHistoryById(Long id, HttpServletRequest request){
        User currentUser = userService.getCurrentUser(request);
        if (currentUser == null){
            throw new IllegalArgumentException("올바르지 않은 접근입니다");
        }
        return purchaseHistoryRepository.findById(id)
                .map(PurchaseHistory::toDTO).orElseThrow(()->new ResourceNotFoundException("찾는 제품 id가 없습니다."));
    }

    public List<PurchaseHistoryDTO> getPurchaseHistoryByProductName(String productName, HttpServletRequest request) {
        User currentUser = userService.getCurrentUser(request);
        if (currentUser == null) {
            throw new IllegalArgumentException("올바르지 않은 접근입니다");
        }
            List<PurchaseHistoryDTO> courseList = purchaseHistoryRepository.findCourseByTitleLike(productName).stream().map(PurchaseHistory::toDTO).toList();
            List<PurchaseHistoryDTO> subscriptionList = purchaseHistoryRepository.findPayrollSubscriptionByTitleLike(productName).stream().map(PurchaseHistory::toDTO).toList();


            List<PurchaseHistoryDTO> resultAll = new ArrayList<>();
            resultAll.addAll(courseList);
            resultAll.addAll(subscriptionList);

            return resultAll;
        }
}