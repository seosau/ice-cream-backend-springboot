package com.project.icecream.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
@Getter
@Entity
@Table(name = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Setter
    @Column(name = "seller_id")
    private int sellerId;
    @Setter
    @Column(name = "name")
    private String name;
    @Setter
    @Column(name = "price")
    private double price;
    @Setter
    @Column(name = "category")
    private String category;
    @Setter
    @Column(name = "image")
    private String image;
    @Setter
    @Column(name = "stock")
    private int stock;
    @Setter
    @Column(name = "product_detail")
    private String productDetail;
    @Setter
    @Column(name = "status")
    private String status;
    @Setter
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Setter
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Products(int sellerId, String name, double price, String category, String image, int stock, String productDetail, String status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.sellerId = sellerId;
        this.name = name;
        this.price = price;
        this.category = category;
        this.image = image;
        this.stock = stock;
        this.productDetail = productDetail;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
