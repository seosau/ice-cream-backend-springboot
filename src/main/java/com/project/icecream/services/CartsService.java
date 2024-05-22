package com.project.icecream.services;

import com.project.icecream.dto.requests.CartRequest;
import com.project.icecream.dto.responses.CartsResponse;
import com.project.icecream.dto.responses.WishlistsResponse;
import com.project.icecream.models.Carts;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartsService {
    List<CartsResponse> addToCart(CartRequest requestBody);
    List<CartsResponse> changeQuantity(int id, int userId, CartRequest requestBody);
    List<CartsResponse> deleteItem(int id, int userId);
    List<CartsResponse> getCarts(int userId);
    List<CartsResponse> getCartsId(int userId);
}
