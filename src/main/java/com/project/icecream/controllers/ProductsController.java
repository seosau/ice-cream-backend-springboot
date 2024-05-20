package com.project.icecream.controllers;

import com.project.icecream.dto.requests.ProductRequest;
import com.project.icecream.dto.responses.ProductResponse;
import com.project.icecream.models.Products;
import com.project.icecream.service_implementors.ProductsImpl;
import com.project.icecream.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping
public class ProductsController {
    @Autowired
    private ProductsImpl productsService;

    @GetMapping("/menu")
    public ResponseEntity<?> getAllProducts(@RequestParam(value = "page", defaultValue = "1") int page) {
        Page<Products> products = productsService.getAllProducts(page, 12);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/viewproduct")
    public ResponseEntity<?> getFilterProducts(@RequestParam(value = "page") int page, @RequestParam(value = "sortBy") String sortBy, @RequestParam(value = "order") String order) {
        Page<Products> products = productsService.getFilterProducts(page, sortBy, order);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/seller/product")
    public ResponseEntity<?> addProduct(@RequestBody ProductRequest requestBody) throws IOException {
        // Lưu sản phẩm vào hệ thống
        Products product = productsService.addProduct(requestBody);
        ProductResponse response = ProductResponse.builder()
                .message("Them san pham thanh cong")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

