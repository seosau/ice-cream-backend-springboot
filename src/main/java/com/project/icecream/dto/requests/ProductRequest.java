package com.project.icecream.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class ProductRequest {
    private int sellerId;
    private String name;
    private double price;
    private String productDetail;
    private String category;
    private int stock;
    private String status;
    private String image;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
