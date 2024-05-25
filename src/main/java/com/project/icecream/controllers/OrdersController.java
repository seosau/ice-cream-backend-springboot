package com.project.icecream.controllers;

import com.project.icecream.dto.requests.OrdersRequest;
import com.project.icecream.dto.responses.OrdersResponse;
import com.project.icecream.models.Orders;
import com.project.icecream.service_implementors.OrdersImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class OrdersController {
    @Autowired
    private OrdersImpl ordersService;

    @GetMapping({"/order", "/seller/order", "/admin/order"})
    public ResponseEntity<?> getOrders(@RequestParam(value = "status", defaultValue = "") String status, @RequestParam(value = "paymentStatus", defaultValue = "") String paymentStatus) {
        List<OrdersResponse> ordersList = ordersService.getOrders(status, paymentStatus);
        return ResponseEntity.ok(ordersList);
    }

    @PutMapping({"/order/{id}","/seller/order/{id}", "/admin/order/{id}"})
    public ResponseEntity<?> updatePaymentStatus(@PathVariable int id, @RequestBody OrdersRequest requestBody) {
        OrdersResponse order = ordersService.changeStatus(id, requestBody);
        return ResponseEntity.ok(order);
    }

    @DeleteMapping({"/seller/order/{id}", "/admin/order/{id}"})
    public ResponseEntity<?> deleteOrder(@PathVariable int id) {
        ordersService.deleteOrder(id);
        return ResponseEntity.ok("Xoa thanh cong");
    }
}
