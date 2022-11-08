package com.nemirovsky.productservice.service;

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

@Service
public class ProductService {

    @Value("${adidas.live.api.domain}")
    private static String ADIDAS_LIVE_API_DOMAIN = "https://www.adidas.co.uk";
    @Value("${adidas.live.api.endpoint}")
    private static String ADIDAS_LIVE_API_ENDPOINT = "/api/products/";

    HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .proxy(ProxySelector.getDefault())
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    public JSONObject findByProductId(String productId) throws IOException, InterruptedException {
        return mergeJSONObjects(getApiInfo(productId, "ReviewsInfo"), getApiInfo(productId, "LiveApi"));
    }

    private JSONObject getApiInfo(String productId, String api) throws IOException, InterruptedException {

        String endpoint = ("LiveApi".equals(api)) ? ADIDAS_LIVE_API_DOMAIN + ADIDAS_LIVE_API_ENDPOINT + productId : "/review/" + productId;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .GET()
                .timeout(Duration.ofSeconds(5))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("An issue occurred making the API call");
        }

        return new JSONObject(response.body());
    }

    private static JSONObject mergeJSONObjects(JSONObject json1, JSONObject json2) {
        JSONObject mergedJSON;
        try {
            mergedJSON = new JSONObject(json1, JSONObject.getNames(json1));
            for (String name : JSONObject.getNames(json2)) {
                mergedJSON.put(name, json2.get(name));
            }
        } catch (JSONException e) {
            throw new RuntimeException("JSON Exception has happened here! " + e);
        }
        return mergedJSON;
    }
}
