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
//    @Query("SELECT p FROM Products p " +
//            "WHERE (:sortBy = 'category' AND p.category = :order) " +
//            "OR (:sortBy = 'price') " +
//            "OR (:sortBy = 'newest') " +
//            "OR (:sortBy = 'bestsale') " +
//            "ORDER BY " +
//            "CASE WHEN :sortBy = 'price' AND :order = 'ASC' THEN p.price END ASC, " +
//            "CASE WHEN :sortBy = 'price' AND :order = 'DESC' THEN p.price END DESC, " +
//            "CASE WHEN :sortBy = 'newest' AND :order = 'ctime' THEN p.updated_at END DESC, " +
//            "CASE WHEN :sortBy = 'bestsale' AND :order = 'ctime' THEN p.stock END ASC "
//
//    )
//
//
//    List<Products> getFilterProducts(@Param("sortBy") String sortBy, @Param("order") String order);


}
