package com.dw.dynamic.service;

import com.dw.dynamic.DTO.UserProductDTO;
import com.dw.dynamic.exception.ResourceNotFoundException;
import com.dw.dynamic.model.UserProduct;
import com.dw.dynamic.repository.PurchaseHistoryRepository;
import com.dw.dynamic.repository.UserProductRepository;
import com.dw.dynamic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProductService {
    @Autowired
    UserProductRepository userProductRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PurchaseHistoryRepository purchaseHistoryRepository;

    public List<UserProductDTO> getAllUserProducts (){
        return userProductRepository.findAll().stream().map(UserProduct::toDTO).toList();
    }

    public UserProductDTO getUserProductById(Long id){
        return userProductRepository.findById(id).map(UserProduct::toDTO)
                .orElseThrow(()-> new ResourceNotFoundException("ID가 없습니다"));
    }

//    public List<UserProductDTO> getUserProductByProductName(String productId){
//        return userProductRepository.findByProductIdLike(productId).stream().map(UserProduct::toDTO).toList();
//    }
}
