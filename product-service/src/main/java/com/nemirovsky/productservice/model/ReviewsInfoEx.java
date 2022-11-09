package com.nemirovsky.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(value = "reviews-info")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ReviewsInfoEx {
    @Id
    private String id;
    private double averageReviewScore;
    private int numberOfReviews;
    private Map<String, Object> liveApiData;
}