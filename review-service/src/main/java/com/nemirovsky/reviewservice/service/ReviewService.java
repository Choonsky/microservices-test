package com.nemirovsky.reviewservice.service;

import com.nemirovsky.reviewservice.model.ReviewsInfo;
import com.nemirovsky.reviewservice.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewsInfo findById(String productId) {
        return Optional.of(reviewRepository.findById(productId)).get()
                .orElseThrow(() -> new ReviewsInfoNotFoundException("Reviews info not found for product ID: " + productId));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ReviewsInfo createReviewsInfo(ReviewsInfo reviewsInfo) {
        log.info("Creating new reviews info for product ID {}", reviewsInfo.getId());
        final ReviewsInfo newReviewsInfo = new ReviewsInfo();
        newReviewsInfo.setId(reviewsInfo.getId());
        newReviewsInfo.setAverageReviewScore(reviewsInfo.getAverageReviewScore());
        newReviewsInfo.setNumberOfReviews(reviewsInfo.getNumberOfReviews());
        return reviewRepository.save(newReviewsInfo);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteReviewsInfo(String productId) {
        log.info("Deleting reviews info for product ID {}", productId);
        reviewRepository.deleteById(productId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ReviewsInfo updateReviewsInfo(Map<String, String> updates, String productId) {
        final ReviewsInfo reviewsInfo = findById(productId);
        updates.keySet()
                .forEach(key -> {
                    switch (key) {
                        case "averageReviewScore" ->
                                reviewsInfo.setAverageReviewScore(Float.parseFloat(updates.get(key)));
                        case "numberOfReviews" -> reviewsInfo.setNumberOfReviews(Integer.parseInt(updates.get(key)));
                    }
                });
        return reviewRepository.save(reviewsInfo);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ReviewsInfo updateReviewsInfo(ReviewsInfo reviewsInfo) {
        return reviewRepository.save(reviewsInfo);
    }
}
