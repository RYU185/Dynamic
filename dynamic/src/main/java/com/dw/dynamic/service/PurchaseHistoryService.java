package com.dw.dynamic.service;

import com.dw.dynamic.DTO.PurchaseHistoryDTO;
import com.dw.dynamic.exception.ResourceNotFoundException;
import com.dw.dynamic.model.PurchaseHistory;
import com.dw.dynamic.repository.PurchaseHistoryRepository;
import com.dw.dynamic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseHistoryService {
    @Autowired
    PurchaseHistoryRepository purchaseHistoryRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    public List<PurchaseHistoryDTO> getAllPurchaseHistorys(){
        return purchaseHistoryRepository.findAll().stream().map(PurchaseHistory::toDTO).toList();
    }

    public PurchaseHistoryDTO getPurchaseHistoryById(Long id){
        PurchaseHistory purchaseHistory = purchaseHistoryRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("존재하지 않는 구매내역 ID입니다."));

        return purchaseHistory.toDTO();
    }

//    public List<PurchaseHistoryDTO> getPurchaseHistoryByProductName(String productName){
//        List<PurchaseHistory> purchaseHistory = purchaseHistoryRepository.findByProductNameLike("%"+productName+"%");
//
//        if (purchaseHistory.isEmpty()){
//            throw new ResourceNotFoundException("존재하지 않는 제품명입니다");
//        }
//        return purchaseHistory.stream().map(PurchaseHistory::toDTO).toList();
//    }

}