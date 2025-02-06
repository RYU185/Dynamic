package com.dw.dynamic.service;

import com.dw.dynamic.DTO.CartDTO;
import com.dw.dynamic.DTO.PurchaseHistoryDTO;
import com.dw.dynamic.DTO.UserProductDTO;
import com.dw.dynamic.exception.InvalidRequestException;
import com.dw.dynamic.exception.PermissionDeniedException;
import com.dw.dynamic.exception.ResourceNotFoundException;
import com.dw.dynamic.model.*;
import com.dw.dynamic.repository.CartRepository;
import com.dw.dynamic.repository.PurchaseHistoryRepository;
import com.dw.dynamic.repository.UserProductRepository;
import com.dw.dynamic.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
            throw new InvalidRequestException("올바르지 않은 접근입니다");
        }

        return userProductRepository.findByUser(currentUser).stream()
                .filter(data->{
                    Product p = data.getProduct();
                    if (p instanceof PayrollSubscription) {
                        if (((PayrollSubscription) p).getExpireDate().isBefore(LocalDate.now())) {
                            return false;
                        }else {
                            return true;
                        }
                    }else {
                        return true;
                    }
                })
                .map(UserProduct::toDTO).toList();

    }

    public UserProductDTO getUserProductById(Long id, HttpServletRequest request) {
        User currentUser = userService.getCurrentUser(request);
        if (currentUser == null) {
            throw new InvalidRequestException("올바르지 않은 접근입니다.");
        }
        UserProduct userProduct = userProductRepository.findById(id).filter(data->{
            Product p = data.getProduct();
            if (p instanceof PayrollSubscription) {
                if (((PayrollSubscription) p).getExpireDate().isBefore(LocalDate.now())) {
                    return false;
                }else {
                    return true;
                }
            }else {
                return true;
            }
        })
                .orElseThrow(()-> new ResourceNotFoundException("조회되지 않은 ID입니다"));

        if (!userProduct.getUser().getUserName().equals(currentUser.getUserName())) {
            throw new PermissionDeniedException("해당 유저제품ID로 존재하는 내역이 없습니다");
        }

        return userProductRepository.findById(id).map(UserProduct::toDTO)
                .orElseThrow(()-> new ResourceNotFoundException("존재하는 ID가 없습니다"));
    }

    public UserProductDTO getUserProductByProductId(String productId, HttpServletRequest request){
        User currentUser = userService.getCurrentUser(request);
        if (currentUser == null){
            throw new InvalidRequestException("올바르지 않은 접근입니다");
        }
        UserProduct userProduct = userProductRepository.findByProductId(productId);
        if (userProduct==null){
            throw new ResourceNotFoundException("해당 제품을 찾을 수 없습니다");
        }
        if (!userProduct.getUser().getUserName().equals(currentUser.getUserName())){
            throw new PermissionDeniedException("해당 제품ID로 존재하는 내역이 없습니다 ");
        }

        Product product = userProduct.getProduct();
        if (product instanceof PayrollSubscription){
            PayrollSubscription payrollSubscription = (PayrollSubscription) product;
            if(payrollSubscription.getExpireDate().isBefore(LocalDate.now())){
                throw new ResourceNotFoundException("구독이 만료된 제품입니다");
            }
        }
        return userProduct.toDTO();
    }

    public List<UserProductDTO> getUserProductByProductName(String productName, HttpServletRequest request) {
        User currentUser = userService.getCurrentUser(request);
        if (currentUser == null) {
            throw new PermissionDeniedException("올바르지 않은 접근입니다");
        }

        List<UserProduct> userProduct = userProductRepository
                .findByProductNameLike(currentUser.getUserName(), productName);
        if (userProduct.isEmpty()) {
            throw new ResourceNotFoundException("검색하신 이름의 제품이 없습니다");
        }

        for (UserProduct userProduct1 : userProduct) {
            Product product = userProduct1.getProduct();

            if (product instanceof PayrollSubscription) {
                PayrollSubscription payrollSubscription = (PayrollSubscription) product;
                if (payrollSubscription.getExpireDate().isBefore(LocalDate.now())) {
                    throw new ResourceNotFoundException("구독이 만료된 제품입니다");
                }
            }
        }
        return userProduct.stream().map(UserProduct::toDTO).toList();
    }
    @Transactional
    public String expireUserProduct(HttpServletRequest request) {
        User currentUser = userService.getCurrentUser(request);
        if (!currentUser.getAuthority().getAuthorityName().equals("ADMIN")){
            throw new InvalidRequestException("권한이 없습니다");
        }

        LocalDate expireDate = LocalDate.now();

        List<UserProduct> expiredProducts = userProductRepository.findExpiredProducts(expireDate);

        userProductRepository.deleteAll(expiredProducts);

        return "만료된 구독권이 정상적으로 삭제되었습니다.";
    }
}
