package com.project.icecream.dto.responses;

import com.project.icecream.models.Products;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class WishlistsResponse {
    private int id;
    private int productId;
    private Products products;
}
