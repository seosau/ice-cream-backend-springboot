package com.project.icecream.services;

import com.project.icecream.models.Products;
import com.project.icecream.repositories.ProductsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.ResourceLoader;
import java.nio.file.Paths;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductsService {
    @Autowired
    private ProductsDAO productsDAO;
    @Autowired
    private ResourceLoader resourceLoader;
    public Products addProduct(Products product) throws IOException {
        // Lưu sản phẩm vào cơ sở dữ liệu
        return productsDAO.save(product);
    }
    public List<Products> getAllProducts() {
        List<Products> products = productsDAO.findAll();

        for (Products product : products) {
            String imagePath = product.getImage();
            if (imagePath != null && !imagePath.isEmpty()) {
                File file = new File(imagePath);
                String fileName = file.getName();
                String imageUrl = "http://localhost:8080/" + fileName;
                product.setImage(imageUrl);
            }
        }
        return products;
    }

    public Optional<Products> getProductById(int id) {
        return productsDAO.findById(id);
    }

    public Products updateProduct(int id, Products updatedProduct) {
        if (productsDAO.existsById(id)) {
            return productsDAO.save(updatedProduct);
        }
        return null;
    }
    public void deleteProduct(int id) {
        productsDAO.deleteById(id);
    }



}
