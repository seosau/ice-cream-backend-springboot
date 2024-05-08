package com.project.icecream.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Entity
@Table(name = "products")
@Data

public class Products {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Setter
    @Column(name = "seller_id")
    private int seller_id;
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
    private String product_detail;
    @Setter
    @Column(name = "status")
    private String status;
    @Setter
    @Column(name = "created_at")
    private Date created_at;
    @Setter
    @Column(name = "updated_at")
    private Date updated_at;

    public Products() {
        this.seller_id = 0;
        this.name = "";
        this.price = 0;
        this.category = "";
        this.image = "";
        this.stock = 0;
        this.product_detail = "";
        this.status = "";
        this.created_at = null;
        this.updated_at = null;
    }

    public Products(int seller_id, String name, double price, String category, String image, int stock, String product_detail, String status, Date created_at, Date updated_at) {
        this.seller_id = seller_id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.image = image;
        this.stock = stock;
        this.product_detail = product_detail;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

}
