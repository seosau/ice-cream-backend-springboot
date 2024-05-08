package com.project.icecream.controllers;

import com.project.icecream.models.Products;
import com.project.icecream.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping
public class ProductsController {
    @Autowired
    private ProductsService productsService;

    @GetMapping("/menu")
    public ResponseEntity<List<Products>> getAllProducts() {
        List<Products> products = productsService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/product/add")
    public ResponseEntity<?> addProduct(@RequestParam("name") String name,
                               @RequestParam("price") double price,
                               @RequestParam("product_detail") String product_detail,
                               @RequestParam("category") String category,
                               @RequestParam("stock") int stock,
                               @RequestParam("status") String status,
                               @RequestParam("image") MultipartFile image) throws IOException {
        Products product = new Products();
        product.setSeller_id(1);
        product.setName(name);
        product.setPrice(price);
        product.setCategory(category);
        product.setProduct_detail(product_detail);
        product.setStock(stock);
        product.setStatus(status);
        String imagePath = saveImageToFileSystem(image);
        product.setImage(imagePath);


        // Gọi phương thức addProduct của productService để thêm sản phẩm và hình ảnh
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    public String saveImageToFileSystem(MultipartFile image) throws IOException {
        final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));
        Path staticPath = Paths.get("src/main/resources/static");
        Path imagePath = Paths.get("images");
        if (!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath))) {
            Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath));
        }
        Path file = CURRENT_FOLDER.resolve(staticPath).resolve(imagePath).resolve(image.getOriginalFilename());
        try (OutputStream os = Files.newOutputStream(file)) {
            os.write(image.getBytes());
        }

        // Trả về đường dẫn của tệp ảnh đã lưu
        return imagePath.resolve(image.getOriginalFilename()).toString();
    }

}

