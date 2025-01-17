package com.dw.dynamic.service;

import com.dw.dynamic.DTO.ReviewDTO;
import com.dw.dynamic.enums.Rating;
import com.dw.dynamic.exception.InvalidRequestException;
import com.dw.dynamic.exception.ResourceNotFoundException;
import com.dw.dynamic.exception.UnauthorizedUserException;
import com.dw.dynamic.model.Review;
import com.dw.dynamic.model.User;
import com.dw.dynamic.repository.ProductRepository;
import com.dw.dynamic.repository.PurchaseHistoryRepository;
import com.dw.dynamic.repository.ReviewRepository;
import com.dw.dynamic.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserService userService;

    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    PurchaseHistoryRepository purchaseHistoryRepository;

    public List<ReviewDTO> getAllReviews() {
        try {
            return reviewRepository.findAll().stream().map(Review::toDTO).toList();
        } catch (InvalidRequestException e) {
            throw new InvalidRequestException("정상적인 요청이 아닙니다");
        }
    }

    public ReviewDTO getReviewById(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("존재하지 않는  리뷰ID입니다"));
        return review.toDTO();
    }

    public List<ReviewDTO> getReviewsByProductId(String productId) {
        //  Product product = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("존재하지 않은 제품번호입니다"));
        try {
            return reviewRepository.findByProductId(productId);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("존재하지 않은 제품번호입니다");
        }
    }
    public ReviewDTO saveReview(ReviewDTO reviewDTO){
        if ( reviewDTO.getProductId().equals(purchaseHistoryRepository.findByProductId(reviewDTO.getProductId()))){
            return reviewRepository.findById(reviewDTO.getId())
                    .map((review) -> {
                        review.setModifiedDate(LocalDateTime.now());
                        return reviewRepository.save(review).toDTO();
                    })
                    .orElseGet(() -> {
                        Review review = new Review(
                                null,
                                reviewDTO.getText(),
                                Rating.EXCELLENT,
                                LocalDateTime.now(),
                                LocalDateTime.now(),
                                true,
                                userRepository.findById(reviewDTO.getUserName()).orElseThrow(() -> new ResourceNotFoundException("없는 유저입니다.")),
                                productRepository.findById(reviewDTO.getProductId()).orElseThrow(() -> new ResourceNotFoundException("존재하지 않은 제품입니다"))
                        );
                        return reviewRepository.save(review).toDTO();
                    });
        }else{
            throw new ResourceNotFoundException("구매하신 제품에 대해서만 리뷰 작성이 가능합니다");
        }

    }

    public String deleteReview(Long id, HttpServletRequest request){
        User currentUser = userService.getCurrentUser(request);
        Review review= reviewRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("존재하지 않은 리뷰입니다."));
        if (!review.getUser().equals(currentUser)){
            throw new UnauthorizedUserException("본인이 작성한 리뷰에 대해서만 삭제할 수 있습니다");
        }
        review.setIsActive(false);
        reviewRepository.save(review);
        return "리뷰가 정상 삭제되었습니다";
    }
}
