package com.dw.dynamic.service;

import com.dw.dynamic.DTO.CartDTO;
import com.dw.dynamic.DTO.ProductDTO;
import com.dw.dynamic.DTO.PurchaseHistoryDTO;
import com.dw.dynamic.DTO.UserProductDTO;
import com.dw.dynamic.exception.InvalidRequestException;
import com.dw.dynamic.exception.PermissionDeniedException;
import com.dw.dynamic.exception.ResourceNotFoundException;
import com.dw.dynamic.exception.UnauthorizedUserException;
import com.dw.dynamic.model.*;
import com.dw.dynamic.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PurchaseHistoryService {
    @Autowired
    PurchaseHistoryRepository purchaseHistoryRepository;

    @Autowired
    UserService userService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserProductRepository userProductRepository;

    @Autowired
    PointService pointService;

    @Autowired
    PointRepository pointRepository;

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

    public List<PurchaseHistoryDTO> getPurchaseHistoryByUserName(String userName, HttpServletRequest request){
        User currentUser = userService.getCurrentUser(request);

        if (currentUser == null){
            throw new IllegalArgumentException("올바르지 않은 접근입니다");
        }
        List<PurchaseHistory> purchaseHistories = purchaseHistoryRepository.findByUser_UserName(userName);

        if (purchaseHistories.isEmpty()){
            throw new ResourceNotFoundException("해당 유저 계정으로 조회되는 구매내역이 없습니다");
        }
        return purchaseHistories.stream().map(PurchaseHistory::toDTO).toList();

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
    @Transactional
    public List<PurchaseHistoryDTO> savePurchaseHistoryAndUserProduct(List<CartDTO> cartDTOS, HttpServletRequest request){
        User currentUser = userService.getCurrentUser(request);
        if (currentUser == null) {
            throw new UnauthorizedUserException("올바르지 않은 접근입니다");
        }

        List<PurchaseHistory> purchaseHistories = new ArrayList<>();
        List<UserProduct> userProductList = new ArrayList<>();
        List<Point> pointList = new ArrayList<>();
        for (CartDTO data : cartDTOS){
            Cart cart1 = cartRepository.findById(data.getCartId()).orElseThrow(()->new ResourceNotFoundException("존재하지 않은 장바구니ID입니다"));
            if (cart1.getIsActive().equals(false)){
                throw new InvalidRequestException("이미 구매한 제품입니다");
            }
            //구매내역 추가
            PurchaseHistory purchaseHistory = new PurchaseHistory();
            purchaseHistory.setPurchaseDate(LocalDate.now());
            purchaseHistory.setUser(currentUser);
            Product product = productRepository.findById(data.getProductId()).orElseThrow(()->new ResourceNotFoundException("존재하지 않은 제품번호입니다"));
            purchaseHistory.setProduct(product);
            purchaseHistory.setPrice(product.getPrice());
            //유저제품 추가
            UserProduct userProduct = new UserProduct();
            userProduct.setUser(currentUser);
            userProduct.setProduct(product);
            //포인트 추가
            Point point = new Point();
            point.setAmount(pointService.calculatePoint(product.getPrice()));
            point.setPurchaseHistory(purchaseHistory);
            point.setAddDate(LocalDate.now());

            cart1.setIsActive(false);
            purchaseHistories.add(purchaseHistory);
            userProductList.add(userProduct);
            pointList.add(point);
        }
                    pointRepository.saveAll(pointList).stream().map(Point::toDTO).toList();
                    userProductRepository.saveAll(userProductList).stream().map(UserProduct::toDTO).toList();
            return purchaseHistoryRepository.saveAll(purchaseHistories).stream().map(PurchaseHistory::toDTO).toList();
        }


    @Transactional
    public PurchaseHistoryDTO instantBuy(String productId, HttpServletRequest request) {
        System.out.println("Received productId: " + productId);
        System.out.println("Is productId null? " + (productId == null));
        System.out.println("productId type: " + (productId != null ? productId.getClass().getName() : "null"));


        User currentUser = userService.getCurrentUser(request);
        if (currentUser == null) {
            throw new UnauthorizedUserException("올바르지 않은 접근입니다");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 제품을 찾을 수 없습니다."));

        // 결제 내역 생성 및 저장
        PurchaseHistory purchaseHistory = new PurchaseHistory();
        purchaseHistory.setUser(currentUser);
        purchaseHistory.setProduct(product);
        purchaseHistory.setPrice(product.getPrice());
        purchaseHistory.setPurchaseDate(LocalDate.now());
        purchaseHistoryRepository.save(purchaseHistory);

        // 사용자 제품 등록
        UserProduct userProduct = new UserProduct();
        userProduct.setUser(currentUser);
        userProduct.setProduct(product);
        userProductRepository.save(userProduct);

        return purchaseHistory.toDTO();
        }

        public UserProductDTO getUserProductDTO(Long userProductId) {
            System.out.println("Received userProductId: " + userProductId);
        UserProduct userProduct = userProductRepository.findById(userProductId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저 제품을 찾을 수 없습니다2."));
        return userProduct.toDTO();
    }
}