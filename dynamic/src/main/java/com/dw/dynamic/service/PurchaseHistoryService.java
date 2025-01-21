package com.dw.dynamic.service;

import com.dw.dynamic.DTO.ProductDTO;
import com.dw.dynamic.DTO.PurchaseHistoryDTO;
import com.dw.dynamic.exception.PermissionDeniedException;
import com.dw.dynamic.exception.ResourceNotFoundException;
import com.dw.dynamic.exception.UnauthorizedUserException;
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
        PurchaseHistory purchaseHistory = purchaseHistoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("존재하지 않은 구매내역ID입니다"));
        if (currentUser == null){
            throw new IllegalArgumentException("올바르지 않은 접근입니다");
        }

        if (!purchaseHistory.getUser().getUserName().equals(currentUser.getUserName())){
            throw new PermissionDeniedException("해당 구매내역ID로 존재하는 내역이 없습니다");
        }
        return purchaseHistoryRepository.findById(id)
                .map(PurchaseHistory::toDTO).orElseThrow(()->new ResourceNotFoundException("찾는 제품 id가 없습니다."));
    }

    public PurchaseHistoryDTO getPurchaseHistoryByProductId(String id, HttpServletRequest request){
        User currentUser = userService.getCurrentUser(request);
        PurchaseHistory purchaseHistory = purchaseHistoryRepository.findByProductId(id);
        if (currentUser == null){
            throw new IllegalArgumentException("올바르지 않은 접근입니다");
        }
        if (purchaseHistory == null) {
            throw new ResourceNotFoundException("해당 제품ID로 조회되는 구매내역이 없습니다");
        }

        if (!purchaseHistory.getUser().getUserName().equals(currentUser.getUserName())){
            throw new PermissionDeniedException("해당 제품ID로 존재하는 내역이 없습니다");
        }else {
            return purchaseHistory.toDTO();
        }

    }

    public List<PurchaseHistoryDTO> getPurchaseHistoryByProductName(String productName, HttpServletRequest request) {
        User currentUser = userService.getCurrentUser(request);
        if (currentUser == null) {
            throw new IllegalArgumentException("올바르지 않은 접근입니다");
        }

        List<PurchaseHistory> purchaseHistory = purchaseHistoryRepository.findByProductNameLike(productName, currentUser.getUserName());
        if (purchaseHistory.isEmpty()){
            throw new ResourceNotFoundException("해당 제품명으로 조회되는 구매내역이 없습니다");
        }
        return purchaseHistory.stream().map(PurchaseHistory::toDTO).toList();
    }
}