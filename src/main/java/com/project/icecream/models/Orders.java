package com.project.icecream.models;

import com.project.icecream.dto.requests.PlaceOrderRequest;
import com.project.icecream.dto.responses.CartsResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
@Builder
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "seller_id")
    private int sellerID;
    @Column(name = "product_id")
    private int productId;
    @Column(name = "name")
    private String name;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "email")
    private String email;
    @Column(name = "address")
    private String address;
    @Column(name = "payment_method")
    private String paymentMethod;
    @Column(name = "price")
    private double price;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "status")
    private String status;
    @Column(name = "payment_status")
    private String paymentStatus;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Orders (PlaceOrderRequest placeOrderRequest, CartsResponse productRequest){
        this.userId = placeOrderRequest.getUser_id();
        this.phoneNumber = placeOrderRequest.getPhone_number();
        this.email = placeOrderRequest.getEmail();
        this.address = placeOrderRequest.getAddress();
        this.paymentMethod = placeOrderRequest.getPayment_method();
        this.name = placeOrderRequest.getUser_name();

        this.sellerID = productRequest.getProducts().getSellerId();
        this.productId = productRequest.getProductId();
        this.price = productRequest.getProducts().getPrice();
        this.quantity = productRequest.getQuantity();
        this.status = "in progress";
        this.paymentStatus = "in progress";

        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
