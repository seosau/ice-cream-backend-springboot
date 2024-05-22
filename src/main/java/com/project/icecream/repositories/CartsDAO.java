package com.project.icecream.repositories;

import com.project.icecream.models.Carts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartsDAO extends JpaRepository<Carts, Integer> {
    List<Carts> findByUserId(int userId);
}
