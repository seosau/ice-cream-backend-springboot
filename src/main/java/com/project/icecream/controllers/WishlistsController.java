package com.project.icecream.controllers;

import com.project.icecream.dto.requests.WishlistRequest;
import com.project.icecream.dto.responses.WishlistsResponse;
import com.project.icecream.models.Users;
import com.project.icecream.models.Wishlists;
import com.project.icecream.service_implementors.WishlistsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class WishlistsController {
    @Autowired
    private WishlistsImpl wishlistsService;
    @PostMapping({"/wishlists"})
    public ResponseEntity<?> addToWishlists(@RequestBody WishlistRequest requestBody) {
        List<Wishlists> wishlists = wishlistsService.addToWishlists(requestBody);
        return ResponseEntity.ok(wishlists);
    }
    @GetMapping({"/wishlists/{userId}"})
    public ResponseEntity<?> getWishlist(@PathVariable int userId) {
        List<WishlistsResponse> wishlist = wishlistsService.getWishlists(userId);
        return ResponseEntity.ok(wishlist);
    }
    @DeleteMapping({"/wishlists/{id}/{userId}"})
    public ResponseEntity<?> deleteItem(@PathVariable int id, @PathVariable int userId) {
        return ResponseEntity.ok(wishlistsService.deleteItem(id, userId));
    }
    @GetMapping({"/quantitywishlists/{userId}"})
    public ResponseEntity<?> getWishlistIds(@PathVariable int userId) {
        List<WishlistsResponse> wishlist = wishlistsService.getWishlistsId(userId);
        return ResponseEntity.ok(wishlist);
    }
}
