package com.nemirovsky.productservice.controller;

import com.nemirovsky.productservice.service.ProductService;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final DiscoveryClient discoveryClient;

    private final ProductService productService;

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public JSONObject findProductInfo(@PathVariable String productId) throws IOException, InterruptedException {
        return productService.findByProductId(productId);
    }
    @RequestMapping("/service-instances/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(
            @PathVariable String applicationName) {
        return this.discoveryClient.getInstances(applicationName);
    }

}

