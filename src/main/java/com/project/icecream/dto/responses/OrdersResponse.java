package com.project.icecream.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdersResponse {
    private int id;
    private String productName;
    private String userName;
    private int quantity;
    private double price;
    private LocalDateTime date;
    private String phoneNumber;
    private String email;
    private String paymentMethod;
    private String address;
    private String status;
    private String paymentStatus;
}
