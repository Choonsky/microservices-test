package com.nemirovsky.reviewservice.repository;

import com.nemirovsky.reviewservice.model.ReviewsInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReviewRepository extends MongoRepository<ReviewsInfo, String> {
}
