package com.project.icecream.services;

import com.project.icecream.dto.requests.CartRequest;
import com.project.icecream.dto.responses.CartsResponse;
import com.project.icecream.dto.responses.WishlistsResponse;
import com.project.icecream.models.Carts;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartsService {
    Carts addToCart(CartRequest requestBody);
    List<Carts> changeQuantity(int id, int quantity);
    void deleteItem(int id);
    List<CartsResponse> getCarts(int userId);
    List<CartsResponse> getCartsId(int userId);
}
