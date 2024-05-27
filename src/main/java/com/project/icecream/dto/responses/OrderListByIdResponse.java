package com.project.icecream.dto.responses;

import com.project.icecream.models.Products;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderListByIdResponse {
    private int id;
    private int productId;
    private int quantity;
    private Products products;
}
