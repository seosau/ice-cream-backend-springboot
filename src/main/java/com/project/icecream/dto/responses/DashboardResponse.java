package com.project.icecream.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponse {
    private long totalMessages;
    private long totalProducts;
    private long totalActiveProducts;
    private long totalInactiveProducts;
    private long totalUserAccounts;
    private long totalSellerAccounts;
    private long totalOrderPlaced;
    private long totalOrderCanceled;
    private long totalOrderConfirmed;


}
