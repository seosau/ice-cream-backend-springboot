package com.project.icecream.service_implementors;

import com.project.icecream.dto.requests.ProductRequest;
import com.project.icecream.models.Carts;
import com.project.icecream.models.Orders;
import com.project.icecream.models.Products;
import com.project.icecream.models.Wishlists;
import com.project.icecream.repositories.CartsDAO;
import com.project.icecream.repositories.OrdersDAO;
import com.project.icecream.repositories.ProductsDAO;
import com.project.icecream.repositories.WishlistsDAO;
import com.project.icecream.services.ProductsService;
import com.project.icecream.websockets.ProductWebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.project.icecream.utils.GenerateSlugUtil.convertToSlug;
import static com.project.icecream.utils.SaveImageBase64Util.saveBase64ImageToFile;

@Service
public class ProductsImpl implements ProductsService {
    @Autowired
    private ProductsDAO productsDAO;
    @Autowired
    private OrdersDAO ordersDAO;
    @Autowired
    private CartsDAO cartsDAO;
    @Autowired
    private WishlistsDAO wishlistsDAO;
    @Autowired
    private ProductWebSocket productWebSocketController;

    @Override
    public List<Products> getProductsByName(String searchValue) {
        List<Products> products = productsDAO.findByNameContaining(searchValue);

        for (Products product : products) {
            String imageUrl = "http://localhost:8080/api/" + product.getImage();
            product.setImage(imageUrl);
        }
        return products;
    }

    @Override
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
        Products savedProduct = productsDAO.save(product);

        // Cập nhật đường dẫn hình ảnh đầy đủ sau khi lưu
        savedProduct.setImage("http://localhost:8080/api/" + savedProduct.getImage());
        // Gửi thông báo WebSocket
        productWebSocketController.sendProductUpdate(savedProduct);

        return savedProduct;
    }

    @Override
    public Page<Products> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Products> productsPage = productsDAO.findAll(pageable);

        for (Products product : productsPage) {
            String imageUrl = "http://localhost:8080/api/" + product.getImage();
            product.setImage(imageUrl);
        }
        return productsPage;
    }

    @Override
    public Page<Products> getFilterProducts(int page, String sortBy, String order) {
        Pageable pageable;
        Page<Products> productsPageFilter;

        if ("loại".equalsIgnoreCase(sortBy)) {
            pageable = PageRequest.of(page - 1, 12);
            productsPageFilter = productsDAO.findByCategory(order, pageable);
        } else if ("bestsale".equalsIgnoreCase(sortBy)) {
            pageable = PageRequest.of(page - 1, 12, Sort.by(Sort.Direction.DESC, "stock"));
            productsPageFilter = productsDAO.findAll(pageable);
        } else if ("newest".equalsIgnoreCase(sortBy)) {
            pageable = PageRequest.of(page - 1, 12, Sort.by(Sort.Direction.DESC, "updatedAt"));
            productsPageFilter = productsDAO.findAll(pageable);
        } else if ("giá".equalsIgnoreCase(sortBy)) {
            Sort.Direction sortDirection = "desc".equalsIgnoreCase(order) || "ctime".equalsIgnoreCase(order) ? Sort.Direction.DESC : Sort.Direction.ASC;
            pageable = PageRequest.of(page - 1, 12, Sort.by(sortDirection, "price"));
            productsPageFilter = productsDAO.findAll(pageable);
        } else if ("trạng thái".equalsIgnoreCase(sortBy)) {
            pageable = PageRequest.of(page - 1, 12);
            productsPageFilter = productsDAO.findByStatus(order, pageable);
        } else {
            pageable = PageRequest.of(page - 1, 12, Sort.by(Sort.Direction.ASC, "id"));
            productsPageFilter = productsDAO.findAll(pageable);
        }

        for (Products product : productsPageFilter) {
            String imageUrl = "http://localhost:8080/api/" + product.getImage();
            product.setImage(imageUrl);
        }
        return productsPageFilter;
    }

    @Override
    public Optional<Products> getProductById(int id) {
        Optional<Products> product = productsDAO.findById(id);
        product.ifPresent(p -> p.setImage("http://localhost:8080/api/" + p.getImage()));
        return product;
    }

    @Override
    public Products updateProduct(int id, ProductRequest requestBody) throws IOException {
        Optional<Products> optionalProduct = productsDAO.findById(id);
        LocalDateTime currentTime = LocalDateTime.now();
        if (optionalProduct.isPresent()) {
            Products product = optionalProduct.get();
            product.setName(requestBody.getName());
            product.setCategory(requestBody.getCategory());
            product.setStatus(requestBody.getStatus());
            if (requestBody.getImage() != null)
                product.setImage(saveBase64ImageToFile(requestBody.getImage(), convertToSlug(requestBody.getName())));
            product.setStock(requestBody.getStock());
            product.setPrice(requestBody.getPrice());
            product.setSellerId(requestBody.getSellerId());
            product.setProductDetail(requestBody.getProductDetail());
            product.setUpdatedAt(currentTime);
            productsDAO.save(product);
            Optional<Products> updatedProduct = productsDAO.findById(id);
            updatedProduct.ifPresent(p -> p.setImage("http://localhost:8080/api/" + p.getImage()));

            // Gửi thông báo WebSocket
            productWebSocketController.sendProductUpdate(updatedProduct.get());

            return updatedProduct.get();
        }
        return null;
    }

    @Override
    public void deleteProduct(int id) {
        List<Orders> orders = ordersDAO.findByProductId(id);
        List<Carts> carts = cartsDAO.findByProductId(id);
        List<Wishlists> wishlists = wishlistsDAO.findByProductId(id);
        for (Orders order : orders) {
            ordersDAO.deleteById(order.getId());
        }
        for (Carts cart : carts) {
            ordersDAO.deleteById(cart.getId());
        }
        for (Wishlists wishlist : wishlists) {
            ordersDAO.deleteById(wishlist.getId());
        }
        productWebSocketController.sendProductDeletion(id);
        productsDAO.deleteById(id);
    }

}
