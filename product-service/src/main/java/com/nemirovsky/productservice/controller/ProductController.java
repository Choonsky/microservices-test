package com.nemirovsky.productservice.controller;

import com.nemirovsky.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ProductController {

    private final ProductService productService;

    @GetMapping("product/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public JSONObject findProductInfo(@PathVariable String productId) throws IOException, InterruptedException {
        return productService.findByProductId(productId);
    }

}

