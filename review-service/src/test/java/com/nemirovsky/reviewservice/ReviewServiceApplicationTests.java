package com.nemirovsky.reviewservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nemirovsky.reviewservice.model.ReviewsInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ReviewServiceApplicationTests {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dpr) {
        dpr.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void createNewReviewsInfo() throws Exception {
        ReviewsInfo newReviewsInfo = ReviewsInfo.builder()
                .id("A12345")
                .averageReviewScore(5.5)
                .numberOfReviews(50)
                .build();
        String requestString = objectMapper.writeValueAsString(newReviewsInfo);
        mockMvc.perform(MockMvcRequestBuilders.post("/review")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestString))
                .andExpect(status().isCreated());
    }

}
