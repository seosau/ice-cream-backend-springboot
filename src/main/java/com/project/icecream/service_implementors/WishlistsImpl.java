package com.project.icecream.service_implementors;

import com.project.icecream.dto.requests.WishlistRequest;
import com.project.icecream.dto.responses.CartsResponse;
import com.project.icecream.dto.responses.WishlistsResponse;
import com.project.icecream.models.Products;
import com.project.icecream.models.Wishlists;
import com.project.icecream.repositories.ProductsDAO;
import com.project.icecream.repositories.WishlistsDAO;
import com.project.icecream.services.WishlistsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WishlistsImpl implements WishlistsService {
    @Autowired
    private WishlistsDAO wishlistsDAO;
    @Autowired
    private ProductsDAO productsDAO;

    @Override
    public List<WishlistsResponse> getWishlists(int userId) {
        List<Wishlists> wishlists = wishlistsDAO.findByUserId(userId);
        List<WishlistsResponse> wishlistsResponse = new ArrayList<>();
        for (Wishlists w : wishlists) {
            Optional<Products> product = productsDAO.findById(w.getProductId());
            if (product.isPresent()) {
                Products p = product.get();
                p.setImage("http://localhost:8080/api/" + p.getImage());
                wishlistsResponse.add(WishlistsResponse.builder()
                        .id(w.getId())
                        .products(p)
                        .build());
            }
        }
        return wishlistsResponse;
    }

    @Override
    public List<Wishlists> addToWishlists(WishlistRequest requestBody) {
        LocalDateTime currentTime = LocalDateTime.now();
        Wishlists wishlists = Wishlists.builder()
                .userId(requestBody.getUserId())
                .productId(requestBody.getProductId())
                .createdAt(currentTime)
                .updatedAt(currentTime)
                .build();
        wishlistsDAO.save(wishlists);
        return wishlistsDAO.findByUserId(requestBody.getUserId());
    }

    @Override
    public List<WishlistsResponse> deleteItem(int id, int userId) {
        wishlistsDAO.deleteById(id);
        return this.getWishlistsId(userId);
    }
    @Override
    public List<WishlistsResponse> getWishlistsId(int userId) {
        List<Wishlists> wishlists = wishlistsDAO.findByUserId(userId);
        List<WishlistsResponse> wishlistsResponse = new ArrayList<>();
        for (Wishlists w : wishlists) {
            Optional<Products> product = productsDAO.findById(w.getProductId());
            product.ifPresent(products -> wishlistsResponse.add(WishlistsResponse.builder()
                    .productId(products.getId())
                    .build()));
        }
        return wishlistsResponse;
    }
}
