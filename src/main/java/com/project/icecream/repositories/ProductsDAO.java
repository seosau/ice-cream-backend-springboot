package com.project.icecream.repositories;

import com.project.icecream.models.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductsDAO extends JpaRepository<Products, Integer> {
    Page<Products> findByCategory(String category, Pageable pageable);
    Page<Products> findByStatus(String status, Pageable pageable);
    List<Products> findByNameContaining(String searchValue);
    long countByCategory(String category);
    long countByStatus(String status);

}
