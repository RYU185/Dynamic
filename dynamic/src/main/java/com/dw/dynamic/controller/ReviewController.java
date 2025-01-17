package com.dw.dynamic.controller;

import com.dw.dynamic.DTO.ReviewDTO;
import com.dw.dynamic.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    @PostMapping("/save")
    public ResponseEntity<ReviewDTO> saveReview(@RequestBody ReviewDTO reviewDTO) {
        return new ResponseEntity<>(
                reviewService.saveReview(reviewDTO),
                HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        return new ResponseEntity<>(
                reviewService.getAllReviews(),
                HttpStatus.OK);
    }

    @GetMapping("/product-id/{productId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByProductId(@PathVariable String productId) {
        return new ResponseEntity<>(
                reviewService.getReviewsByProductId(productId),
                HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public  ResponseEntity<ReviewDTO> getReviewById(@PathVariable Long id){
        return new ResponseEntity<>(
                reviewService.getReviewById(id),
                HttpStatus.OK
        );
    }
    @PostMapping("/id/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id, HttpServletRequest request){
        return new ResponseEntity<>(
                reviewService.deleteReview(id,request),
                HttpStatus.OK
        );
    }
}
