package com.project.icecream.services;

import com.project.icecream.dto.requests.CartRequest;
import com.project.icecream.dto.requests.WishlistRequest;
import com.project.icecream.dto.responses.WishlistsResponse;
import com.project.icecream.models.Carts;
import com.project.icecream.models.Wishlists;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WishlistsService {
    List<Wishlists> addToWishlists(WishlistRequest requestBody);
    void deleteItem(int id);
    List<WishlistsResponse> getWishlists(int userId);
    List<WishlistsResponse> getWishlistsId(int userId);
}
