package com.nemirovsky.reviewservice.controller;

import com.nemirovsky.reviewservice.model.ReviewsInfo;
import com.nemirovsky.reviewservice.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ReviewsInfo findReviewsInfo(@PathVariable String productId) {
        return reviewService.findById(productId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewsInfo createReviewsInfo(@RequestBody ReviewsInfo reviewsInfo) {
        return reviewService.createReviewsInfo(reviewsInfo);
    }

    @DeleteMapping("/{productId}")
    public void deleteReviewsInfo(@PathVariable String productId) {
        reviewService.deleteReviewsInfo(productId);
    }

    @PutMapping
    public ReviewsInfo updateReviewsInfo(@RequestBody ReviewsInfo reviewsInfo) {
        return reviewService.updateReviewsInfo(reviewsInfo);
    }

    @PatchMapping("/{productId}")
    public ReviewsInfo updateReviewsInfo(@RequestBody Map<String, String> updates, @PathVariable String productId) {
        return reviewService.updateReviewsInfo(updates, productId);
    }
}

