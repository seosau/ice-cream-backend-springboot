package com.project.icecream.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class WishlistRequest {
    private int id;
    private int userId;
    private int productId;
}
