package com.project.icecream.services;

import com.project.icecream.dto.requests.ProductRequest;
import com.project.icecream.models.Products;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Optional;

@Service
public interface ProductsService {
    public Products addProduct(ProductRequest requestBody) throws IOException;
    public Page<Products> getAllProducts(int page, int size);
    public Page<Products> getFilterProducts(int page, String sortBy, String order);
    public Optional<Products> getProductById(int id) throws IOException;
    public Products updateProduct(int id, ProductRequest requestBody) throws IOException;
    public void deleteProduct(int id) throws IOException;
}
