package com.project.icecream.controllers;

import com.project.icecream.dto.requests.OrdersRequest;
import com.project.icecream.dto.requests.ProductRequest;
import com.project.icecream.dto.responses.OrdersResponse;
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
import java.util.Optional;

@RestController
@RequestMapping
public class ProductsController {
    @Autowired
    private ProductsImpl productsService;

    @GetMapping({"/searchproduct/{searchValue}"})
    public ResponseEntity<?> getAllProducts(@PathVariable String searchValue) {
        List<Products> products = productsService.getProductsByName(searchValue);
        return ResponseEntity.ok(products);
    }

    @GetMapping({"/viewproduct", "/seller/viewproduct", "/admin/viewproduct"})
    public ResponseEntity<?> getFilterProducts(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "sortBy", defaultValue = "id") String sortBy, @RequestParam(value = "order", defaultValue = "ASC") String order) {
        Page<Products> products = productsService.getFilterProducts(page, sortBy, order);
        return ResponseEntity.ok(products);
    }

    @GetMapping({"/seller/product", "/admin/product"})
    public ResponseEntity<?> getFilterProducts(@RequestParam(value = "page", defaultValue = "1") int page) {
        Page<Products> products = productsService.getAllProducts(page, 12);
        return ResponseEntity.ok(products);
    }

    @PostMapping({"/seller/product", "/admin/product"})
    public ResponseEntity<?> addProduct(@RequestBody ProductRequest requestBody) throws IOException {
        // Lưu sản phẩm vào hệ thống
        Products product = productsService.addProduct(requestBody);
        ProductResponse response = ProductResponse.builder()
                .message("Them san pham thanh cong")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping({"/menu/{id}", "/seller/product/{id}", "/admin/product/{id}"})
    public ResponseEntity<?> getProductById(@PathVariable int id) {
        Optional<Products> product = productsService.getProductById(id);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }

    @PutMapping({"/seller/product/{id}", "/admin/product/{id}"})
    public ResponseEntity<?> updatePaymentStatus(@PathVariable int id, @RequestBody ProductRequest requestBody) throws IOException {
        Products product = productsService.updateProduct(id, requestBody);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping({"/seller/product/{id}", "/admin/product/{id}"})
    public ResponseEntity<?> deleteProduct(@PathVariable int id) {
        productsService.deleteProduct(id);
        return ResponseEntity.ok("Xoa thanh cong");
    }
}

