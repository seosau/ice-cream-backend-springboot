package com.project.icecream.repositories;
import com.project.icecream.models.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsDAO extends JpaRepository<Products, Integer> {
}
