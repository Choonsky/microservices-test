package com.nemirovsky.reviewservice;

import com.nemirovsky.reviewservice.model.ReviewsInfo;
import com.nemirovsky.reviewservice.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class ReviewServiceApplication implements CommandLineRunner {

    private final ReviewRepository reviewRepository;

    public static void main(String[] args) {
        SpringApplication.run(ReviewServiceApplication.class, args);
    }

    @Override
    public void run(String... args) {
        if (reviewRepository.count() < 1) {
            log.info("Starting initial DB seed...");
            ReviewsInfo reviewsInfo = new ReviewsInfo("M20324", 4.5, 18);
            reviewRepository.save(reviewsInfo);
            ReviewsInfo reviewsInfo1 = new ReviewsInfo("AC7836", 6.2, 7);
            reviewRepository.save(reviewsInfo1);
            ReviewsInfo reviewsInfo2 = new ReviewsInfo("C77154", 5.9, 21);
            reviewRepository.save(reviewsInfo2);
            ReviewsInfo reviewsInfo3 = new ReviewsInfo("BB5476", 4.3, 13);
            reviewRepository.save(reviewsInfo3);
            ReviewsInfo reviewsInfo4 = new ReviewsInfo("B42000", 7.8, 10);
            reviewRepository.save(reviewsInfo4);
            log.info("Some entries were added to DB.");
        };
    }
}