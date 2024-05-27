package com.project.icecream.dto.requests;

import com.project.icecream.dto.responses.CartsResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PlaceOrderRequest {
    private int user_id;
    private String user_name;
    private String phone_number;
    private String email;
    private String address;
    private String payment_method;
    private List<CartsResponse> products;
}
