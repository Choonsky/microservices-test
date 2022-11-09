package com.nemirovsky.productservice.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nemirovsky.productservice.model.ReviewsInfoEx;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private static EurekaClient eurekaClient;

    @Value("${adidas.live.api.domain}")
    private static String ADIDAS_LIVE_API_DOMAIN = "https://www.adidas.co.uk";
    @Value("${adidas.live.api.endpoint}")
    private static String ADIDAS_LIVE_API_ENDPOINT = "/api/products/";

    HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .proxy(ProxySelector.getDefault())
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    public Map<String, Object> findByProductId(String productId) throws IOException, InterruptedException {
        return aggregateData(getReviewsInfo(productId), getApiInfo(productId));
    }

    private Map<String, Object> getApiInfo(String productId) throws IOException, InterruptedException {

        String endpoint = ADIDAS_LIVE_API_DOMAIN + ADIDAS_LIVE_API_ENDPOINT + productId;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .GET()
                .timeout(Duration.ofSeconds(5))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 404) {
            throw new RuntimeException("An issue occurred making the API call to " + request.uri() + "!");
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(
                    response.body(), new TypeReference<Map<String, Object>>() {
                    });
        } catch (Exception e) {
            log.error("Error mapping JSON object to Map!");
            e.printStackTrace();
        }
        return null;
    }
    private Map<String, Object> getReviewsInfo(String productId) throws IOException, InterruptedException {

        String endpoint = getReviewHostName(getReviewService()) + ":" + getReviewHostPort(getReviewService()) + "/review/" + productId;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .GET()
                .timeout(Duration.ofSeconds(5))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 404) {
            throw new RuntimeException("An issue occurred making the API call to " + request.uri() + "!");
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(
                    response.body(), new TypeReference<Map<String, Object>>() {
                    });
        } catch (Exception e) {
            log.error("Error mapping JSON object to Map!");
            e.printStackTrace();
        }
        return null;
    }

    private InstanceInfo getReviewService() {
        if (eurekaClient == null) return null;
        Application app = eurekaClient
                .getApplication("review-service");
        if (app != null && app.getInstances().size() > 0) {
            return app.getInstances().get(0);
        } else return null;
    }

    private String getReviewHostPort(InstanceInfo service) {
        return service == null ? "8081" : String.valueOf(service.getPort());
    }

    private String getReviewHostName(InstanceInfo service) {
        return service == null ? "http://localhost" : service.getHostName();
    }

    private static Map<String, Object> aggregateData(Map<String, Object> map1, Map<String, Object> map2) {
        return Stream.of(map1, map2)
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (value1, value2) -> value1));
    }
}
