package com.nemirovsky.reviewservice.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
class ReviewsInfoNotFoundException extends RuntimeException {
    ReviewsInfoNotFoundException(String message) {
        super(message);
    }
}