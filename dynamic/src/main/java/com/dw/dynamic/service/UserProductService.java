package com.dw.dynamic.service;

import com.dw.dynamic.DTO.CartDTO;
import com.dw.dynamic.DTO.PurchaseHistoryDTO;
import com.dw.dynamic.DTO.UserProductDTO;
import com.dw.dynamic.exception.InvalidRequestException;
import com.dw.dynamic.exception.PermissionDeniedException;
import com.dw.dynamic.exception.ResourceNotFoundException;
import com.dw.dynamic.model.PurchaseHistory;
import com.dw.dynamic.model.User;
import com.dw.dynamic.model.UserProduct;
import com.dw.dynamic.repository.CartRepository;
import com.dw.dynamic.repository.PurchaseHistoryRepository;
import com.dw.dynamic.repository.UserProductRepository;
import com.dw.dynamic.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProductService {
    @Autowired
    UserProductRepository userProductRepository;
    @Autowired
    UserService userService;
    @Autowired
    PurchaseHistoryService purchaseHistoryService;


    public List<UserProductDTO> getAllUserProducts (HttpServletRequest request){
        User currentUser = userService.getCurrentUser(request);
        if (currentUser == null){
            throw new IllegalArgumentException("올바르지 않은 접근입니다");
        }
        return userProductRepository.findByUser(currentUser).stream().map(UserProduct::toDTO).toList();

    }

    public UserProductDTO getUserProductById(Long id, HttpServletRequest request) {
        User currentUser = userService.getCurrentUser(request);
        if (currentUser == null) {
            throw new IllegalArgumentException("올바르지 않은 접근입니다.");
        }
        UserProduct userProduct = userProductRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("올바르지 않은 접근입니다."));

        if (!userProduct.getUser().getUserName().equals(currentUser.getUserName())) {
            throw new PermissionDeniedException("해당 유저제품ID로 존재하는 내역이 없습니다");
        }

        return userProductRepository.findById(id).map(UserProduct::toDTO)
                .orElseThrow(()-> new ResourceNotFoundException("존재하는 ID가 없습니다"));
    }

    public UserProductDTO getUserProductByProductId(String productId, HttpServletRequest request){
        User currentUser = userService.getCurrentUser(request);
        if (currentUser == null){
            throw new IllegalArgumentException("올바르지 않은 접근입니다");
        }
        UserProduct userProduct = userProductRepository.findByProductId(productId);
        if (userProduct==null){
            throw new ResourceNotFoundException("해당 제품을 찾을 수 없습니다");
        }
        if (!userProduct.getUser().getUserName().equals(currentUser.getUserName())){
            throw new PermissionDeniedException("해당 제품ID로 존재하는 내역이 없습니다 ");
        }
        return userProduct.toDTO();
    }

    public List<UserProductDTO> getUserProductByProductName(String productName, HttpServletRequest request){
        User currentUser = userService.getCurrentUser(request);
        if (currentUser == null){
            throw new IllegalArgumentException("올바르지 않은 접근입니다");
        }

        List<UserProduct> userProducts = userProductRepository.findByProductNameLike(currentUser.getUserName(), productName);

        return userProducts.stream().map(UserProduct::toDTO).toList();
    }


}
