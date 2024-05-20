package com.project.icecream.service_implementors;

import com.project.icecream.dto.requests.ProductRequest;
import com.project.icecream.models.Products;
import com.project.icecream.repositories.ProductsDAO;
import com.project.icecream.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.project.icecream.utils.GenerateSlugUtil.convertToSlug;
import static com.project.icecream.utils.SaveImageBase64Util.saveBase64ImageToFile;

@Service
public class ProductsImpl implements ProductsService {
    @Autowired
    private ProductsDAO productsDAO;

    public Products addProduct(ProductRequest requestBody) throws IOException {
        LocalDateTime currentTime = LocalDateTime.now(); // Lấy thời gian hiện tại
        Products product = Products.builder()
                .sellerId(requestBody.getSellerId())
                .name(requestBody.getName())
                .price(requestBody.getPrice())
                .category(requestBody.getCategory())
                .productDetail(requestBody.getProductDetail())
                .stock(requestBody.getStock())
                .status(requestBody.getStatus())
                .image(saveBase64ImageToFile(requestBody.getImage(), convertToSlug(requestBody.getName())))
                .createdAt(currentTime)
                .updatedAt(currentTime)
                .build();
        // Lưu sản phẩm vào cơ sở dữ liệu
        return productsDAO.save(product);
    }
    public Page<Products> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Products> productsPage = productsDAO.findAll(pageable);

        for (Products product : productsPage) {
            String imageUrl = "http://localhost:8080/api/" + product.getImage();
            product.setImage(imageUrl);
        }
        return productsPage;
    }

    public Page<Products> getFilterProducts(int page, String sortBy, String order) {
        Pageable pageable;
        Page<Products> productsPageFilter;

        if ("loại".equalsIgnoreCase(sortBy)) {
            pageable = PageRequest.of(page - 1, 12);
            productsPageFilter = productsDAO.findByCategory(order, pageable);
        }
        else if ("bestsale".equalsIgnoreCase(sortBy)) {
            pageable = PageRequest.of(page - 1, 12, Sort.by(Sort.Direction.DESC,"stock"));
            productsPageFilter = productsDAO.findAll(pageable);
        }
        else if ("newest".equalsIgnoreCase(sortBy)) {
            pageable = PageRequest.of(page - 1, 12, Sort.by(Sort.Direction.DESC,"updatedAt"));
            productsPageFilter = productsDAO.findAll(pageable);
        }
        else {
            Sort.Direction sortDirection = "desc".equalsIgnoreCase(order) || "ctime".equalsIgnoreCase(order) ? Sort.Direction.DESC : Sort.Direction.ASC;
            pageable = PageRequest.of(page - 1, 12, Sort.by(sortDirection, "price"));
            productsPageFilter = productsDAO.findAll(pageable);
        }

        for (Products product : productsPageFilter) {
            String imagePath = product.getImage();
            if (imagePath != null && !imagePath.isEmpty()) {
                File file = new File(imagePath);
                String fileName = file.getName();
                String imageUrl = "http://localhost:8080/api/" + fileName;
                product.setImage(imageUrl);
            }
        }
        return productsPageFilter;
    }



    public Optional<Products> getProductById(int id) {
        return productsDAO.findById(id);
    }

    public Products updateProduct(int id, ProductRequest requestBody) throws IOException {
        LocalDateTime currentTime = LocalDateTime.now(); // Lấy thời gian hiện tại
        if (productsDAO.existsById(id)) {
            Products product = Products.builder()
                    .sellerId(requestBody.getSellerId())
                    .name(requestBody.getName())
                    .price(requestBody.getPrice())
                    .category(requestBody.getCategory())
                    .productDetail(requestBody.getProductDetail())
                    .stock(requestBody.getStock())
                    .status(requestBody.getStatus())
                    .image(saveBase64ImageToFile(requestBody.getImage(), convertToSlug(requestBody.getName())))
                    .updatedAt(currentTime)
                    .build();
            return productsDAO.save(product);
        }
        return null;
    }
    public void deleteProduct(int id) {
        productsDAO.deleteById(id);
    }

}
