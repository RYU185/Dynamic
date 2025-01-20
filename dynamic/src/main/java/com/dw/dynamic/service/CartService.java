package com.dw.dynamic.service;

import com.dw.dynamic.DTO.CartDTO;
import com.dw.dynamic.exception.InvalidRequestException;
import com.dw.dynamic.exception.ResourceNotFoundException;
import com.dw.dynamic.model.Cart;
import com.dw.dynamic.model.User;
import com.dw.dynamic.repository.CartRepository;
import com.dw.dynamic.repository.ProductRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserService userService;

    public List<CartDTO> getAllCarts(HttpServletRequest request){
        User currentUser = userService.getCurrentUser(request);
        return cartRepository.findByUser(currentUser).stream().map(Cart::toDTO).toList();
    }

    public CartDTO getCartById(Long id, HttpServletRequest request) {
        User currentUser = userService.getCurrentUser(request);
        Cart cart = cartRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("존재하지 않는 ID입니다"));

        if(!cart.getUser().equals(currentUser)){
            throw new InvalidRequestException("정상적인 요청이 아닙니다");
        }
        return cart.toDTO();
    }

    public CartDTO saveCart(CartDTO cartDTO, HttpServletRequest request){
        User currentUser = userService.getCurrentUser(request);

        Cart cart = new Cart();
        cart.setUser(currentUser);
        if (cartDTO.getProduct() == null || cartDTO.getProduct().getId() == null) {
            throw new IllegalArgumentException("제품 정보가 올바르지 않습니다.");
        }
        cart.setIsActive(true);

        return cartRepository.save(cart).toDTO();
    }

    public String deleteCart(Long id, HttpServletRequest request){
        User currentUser = userService.getCurrentUser(request);

        Cart cart = cartRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("id를 찾을 수 없습니다"));

        if(!cart.getIsActive()){
            throw new InvalidRequestException("이미 삭제된 장바구니입니다");
        }

        if (!cart.getUser().equals(currentUser)){
            throw new InvalidRequestException("장바구니는 로그인이 필요합니다.");
        }

        cart.setIsActive(false);
        cartRepository.save(cart);
        return id + " 가 정상적으로 삭제되었습니다";
    }


}



