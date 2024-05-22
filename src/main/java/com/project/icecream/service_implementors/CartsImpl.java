package com.project.icecream.service_implementors;

import com.project.icecream.dto.requests.CartRequest;
import com.project.icecream.dto.responses.CartsResponse;
import com.project.icecream.dto.responses.WishlistsResponse;
import com.project.icecream.models.Carts;
import com.project.icecream.models.Products;
import com.project.icecream.models.Wishlists;
import com.project.icecream.repositories.CartsDAO;
import com.project.icecream.repositories.ProductsDAO;
import com.project.icecream.services.CartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartsImpl implements CartsService {
    @Autowired
    private CartsDAO cartsDAO;
    @Autowired
    private ProductsDAO productsDAO;

    @Override
    public Carts addToCart(CartRequest requestBody) {
        LocalDateTime currentTime = LocalDateTime.now();
        Carts cart = Carts.builder()
                .userId(requestBody.getUserId())
                .productId(requestBody.getProductId())
                .quantity(requestBody.getQuantity())
                .createdAt(currentTime)
                .updatedAt(currentTime)
                .build();
        return cartsDAO.save(cart);
    }

    @Override
    public List<Carts> changeQuantity(int id, int quantity) {
        Optional<Carts> optionalCarts = cartsDAO.findById(id);
        if (optionalCarts.isPresent()) {
            Carts cart = optionalCarts.get();
            cart.setQuantity(quantity);
            cartsDAO.save(cart);
            return cartsDAO.findAll();
        }
        return null;
    }

    @Override
    public void deleteItem(int id) {
        cartsDAO.deleteById(id);
    }

    @Override
    public List<CartsResponse> getCarts(int userId) {
        List<Carts> carts = cartsDAO.findByUserId(userId);
        List<CartsResponse> cartsResponses = new ArrayList<>();
        for (Carts c : carts) {
            Optional<Products> product = productsDAO.findById(c.getProductId());
            if (product.isPresent()) {
                Products p = product.get();
                p.setImage("http://localhost:8080/api/" + p.getImage());
                cartsResponses.add(CartsResponse.builder()
                        .id(c.getId())
                        .products(p)
                        .build());
            }
        }
        return cartsResponses;
    }

    @Override
    public List<CartsResponse> getCartsId(int userId) {
        List<Carts> carts = cartsDAO.findByUserId(userId);
        List<CartsResponse> cartsResponses = new ArrayList<>();
        for (Carts c : carts) {
            Optional<Products> product = productsDAO.findById(c.getProductId());
            product.ifPresent(products -> cartsResponses.add(CartsResponse.builder()
                    .productId(products.getId())
                    .build()));
        }
        return cartsResponses;
    }
}
