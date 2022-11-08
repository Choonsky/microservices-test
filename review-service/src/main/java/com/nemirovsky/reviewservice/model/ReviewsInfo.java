package com.nemirovsky.reviewservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "reviews-info")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ReviewsInfo {
    @Id
    private String id;
    private double averageReviewScore;
    private int numberOfReviews;
}