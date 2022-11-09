package com.nemirovsky.productservice.controller;

import com.nemirovsky.productservice.model.ReviewsInfoEx;
import com.nemirovsky.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final DiscoveryClient discoveryClient;

    private final ProductService productService;

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> findProductInfo(@PathVariable String productId) throws IOException, InterruptedException {
        return productService.findByProductId(productId);
    }

}

