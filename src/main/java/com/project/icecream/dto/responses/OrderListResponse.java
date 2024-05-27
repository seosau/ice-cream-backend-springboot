package com.project.icecream.dto.responses;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderListResponse {
    private List<OrdersResponse> orderList;
}
