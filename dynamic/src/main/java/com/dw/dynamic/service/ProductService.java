package com.dw.dynamic.service;

import com.dw.dynamic.DTO.CategoryEnrollmentAndIncomeDTO;
import com.dw.dynamic.DTO.CourseEnrollmentAndIncomeDTO;
import com.dw.dynamic.DTO.PayrollSubscriptionsEnrollmentAndIncomeDTO;
import com.dw.dynamic.DTO.ProductDTO;
import com.dw.dynamic.exception.InvalidRequestException;
import com.dw.dynamic.exception.PermissionDeniedException;
import com.dw.dynamic.exception.ResourceNotFoundException;
import com.dw.dynamic.model.*;
import com.dw.dynamic.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserService userService;
    @Autowired
    CartRepository cartRepository;

    public List<ProductDTO> getAllProducts (){
        return productRepository.findAll().stream().map(Product::toDTO).toList();
    }

    public List<ProductDTO> getProductsByTitle(String title){
        List<ProductDTO> courseList = productRepository.findCourseByTitleLike(title).stream().map(Product::toDTO).filter(productDTO -> productDTO.getIsActive().equals(true)).toList();
        List<ProductDTO> subscriptionList =productRepository.findPayrollSubscriptionByTitleLike(title).stream().map(Product::toDTO).filter(productDTO -> productDTO.getIsActive().equals(true)).toList();

        List<ProductDTO> resultAll = new ArrayList<>();
        resultAll.addAll(courseList);
        resultAll.addAll(subscriptionList);

        return resultAll;
    }

    public ProductDTO getProductById(String id){
        ProductDTO productDTO = productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("존재하지 않은 ID입니다.")).toDTO();
        if (productDTO.getIsActive().equals(false)){
            throw new ResourceNotFoundException("존재하지 않은 ID입니다");
        }
        return productDTO;
    }

    // 관리자 권한으로 강의 추가 // 결제 버튼을 누르면 product 추가
    public Product saveProduct(Product product, HttpServletRequest request) {
        User currentUser = userService.getCurrentUser(request);

        if (product instanceof Course) {
            if (!currentUser.getAuthority().getAuthorityName().equals("ADMIN")) {
                throw new PermissionDeniedException("권한이 없습니다");
            }
            Course course = (Course) product;
        } else {
            PayrollSubscription payrollSubscription = (PayrollSubscription) product;
        }
        return productRepository.save(product);
    }
    // 관리자 권한으로 제품 삭제
    @Transactional
    public String deleteProduct(String id,HttpServletRequest request){
        User currentUser = userService.getCurrentUser(request);
        if(!currentUser.getAuthority().getAuthorityName().equals("ADMIN")){
            throw new PermissionDeniedException("권한이 없습니다");
        }
        Product product = productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("존재하지 않는 ID입니다"));
        List<Cart> carts = cartRepository.findByProductId(id);
        for (Cart cart: carts){
            if (cart.getIsActive().equals(true)) {
                cart.setIsActive(false);
            }
            cartRepository.save(cart);
        }
        product.setIsActive(false);
        productRepository.save(product);
        return "정상 삭제되었습니다";
    }

    public List<CourseEnrollmentAndIncomeDTO> getCoursesEnrollmentsAndIncomes(HttpServletRequest request){
        try {
            return productRepository.getCoursesEnrollmentsAndIncomes();
        }catch (InvalidRequestException e){
            throw new InvalidRequestException("정상적인 요청이 아닙니다");
        }

    }

    public List<PayrollSubscriptionsEnrollmentAndIncomeDTO> getPayrollSubscriptionsEnrollmentsAndIncomes(HttpServletRequest request){
        User currentUser = userService.getCurrentUser(request);
        if(!currentUser.getAuthority().getAuthorityName().equals("ADMIN")){
            throw new PermissionDeniedException("권한이 없습니다");
        }
        try {
            return productRepository.getPayrollSubscriptionsEnrollmentsAndIncomes();
        }catch (InvalidRequestException e){
            throw new InvalidRequestException("정상적인 요청이 아닙니다");
        }
    }

    public List<CategoryEnrollmentAndIncomeDTO> getCategoryEnrollmentsAndIncomes(HttpServletRequest request){
        User currentUser = userService.getCurrentUser(request);
        if(!currentUser.getAuthority().getAuthorityName().equals("ADMIN")){
            throw new PermissionDeniedException("권한이 없습니다");
        }
        try {
            return productRepository.getCategoryEnrollmentsAndIncomes();
        }catch (InvalidRequestException e){
            throw new InvalidRequestException("정상적인 요청이 아닙니다");
        }
    }
}