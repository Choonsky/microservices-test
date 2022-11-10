package com.nemirovsky.reviewservice.kafka;

import com.nemirovsky.reviewservice.model.ReviewsInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaSender {

    private static KafkaTemplate<String, ReviewsInfo> kafkaTemplate;

    String kafkaTopic = "productRequests";

    public void send(ReviewsInfo reviewsInfo) {
        kafkaTemplate.send(kafkaTopic, reviewsInfo);
    }
}