package com.nemirovsky.productservice.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaSender {

    private static KafkaTemplate<String, String> kafkaTemplate;

    String kafkaTopic = "productRequests";

    public void send(String message) {
        kafkaTemplate.send(kafkaTopic, message);
    }
}