package com.project.icecream.repositories;

import com.project.icecream.models.Carts;
import com.project.icecream.models.Wishlists;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistsDAO extends JpaRepository<Wishlists, Integer> {
    List<Wishlists> findByUserId(int userId);
    List<Wishlists> findByProductId(int productId);
}
