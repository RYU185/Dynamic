package com.dw.dynamic.controller;

import com.dw.dynamic.DTO.ReviewDTO;
import com.dw.dynamic.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class ReviewController {
    @Autowired
    ReviewService reviewService;

//    @PostMapping("/save")
//    public ResponseEntity<ReviewDTO> saveReview(@RequestBody ReviewDTO reviewDTO) {
//        return new ResponseEntity<>(
//                reviewService.saveReview(reviewDTO),
//                HttpStatus.OK);
//    }
//
//    @GetMapping("/all")
//    public ResponseEntity<List<ReviewDTO>> getReviewAll() {
//        return new ResponseEntity<>(
//                reviewService.getReviewAll(),
//                HttpStatus.OK);
//    }
//
//    @GetMapping("/product/id/{productid}")
//    public ResponseEntity<List<ReviewDTO>> getReviewsByProductId(@PathVariable long gameId) {
//        return new ResponseEntity<>(
//        reviewService.getReviewsByProductId(productId),
//        HttpStatus.OK);
//    }
}
