package com.project.icecream.services;

import com.project.icecream.dto.requests.OrdersRequest;
import com.project.icecream.dto.responses.OrdersResponse;
import com.project.icecream.models.Orders;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface OrdersService {
    List<OrdersResponse> getOrders(String status, String paymentStatus);
    void deleteOrder(int id);
    OrdersResponse changeStatus(int id, OrdersRequest requestBody);
}
