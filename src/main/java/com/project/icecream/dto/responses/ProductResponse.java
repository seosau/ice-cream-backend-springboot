package com.project.icecream.dto.responses;

import com.project.icecream.models.Products;
import lombok.*;

import java.util.ArrayList;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class ProductResponse {
    protected String message;
    protected ArrayList<Products> data;
}
