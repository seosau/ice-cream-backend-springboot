package com.project.icecream.service_implementors;

import com.project.icecream.dto.requests.OrdersRequest;
import com.project.icecream.dto.responses.OrdersResponse;
import com.project.icecream.models.Users;
import com.project.icecream.models.Orders;
import com.project.icecream.models.Products;
import com.project.icecream.models.Users;
import com.project.icecream.repositories.UserDAO;
import com.project.icecream.repositories.OrdersDAO;
import com.project.icecream.repositories.ProductsDAO;
import com.project.icecream.services.OrdersService;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrdersImpl implements OrdersService {
    @Autowired
    private OrdersDAO ordersDAO;

    @Autowired
    private ProductsDAO productsDAO;

    @Autowired
    private UserDAO userDAO;

    public List<OrdersResponse> getOrders(String status, String paymentStatus) {
        List<Orders> orders = null;
        List<OrdersResponse> ordersResponses = new ArrayList<>();
        if (status.equalsIgnoreCase("đã giao") || status.equalsIgnoreCase("đã hủy")) {
            orders = ordersDAO.findByStatusAndPaymentStatus(status, paymentStatus);
        } else {
            orders = ordersDAO.findAll();
        }

        for (Orders order : orders) {
            Optional<Products> productOptional = productsDAO.findById(order.getProductId());
            Optional<Users> userOptional = userDAO.findById(order.getUserId());

            String productName = productOptional.map(Products::getName).orElse("Unknown");
            String userName = userOptional.map(Users::getName).orElse("Unknown");

            ordersResponses.add(OrdersResponse.builder()
                    .id(order.getId())
                    .productName(productName)
                    .userName(userName)
                    .quantity(order.getQuantity())
                    .date(order.getCreatedAt())
                    .phoneNumber(order.getPhoneNumber())
                    .email(order.getEmail())
                    .price(order.getPrice())
                    .paymentMethod(order.getPaymentMethod())
                    .address(order.getAddress())
                    .paymentStatus(order.getPaymentStatus())
                    .status(order.getStatus())
                    .build());
        }
        return ordersResponses;
    }

    public OrdersResponse changeStatus(int id, OrdersRequest requestBody) {
        Optional<Orders> optionalOrder = ordersDAO.findById(id);

        if (optionalOrder.isPresent()) {
            Orders order = optionalOrder.get();
            order.setPaymentStatus(requestBody.getPaymentStatus());
            order.setStatus(requestBody.getStatus());
            ordersDAO.save(order);

            return OrdersResponse.builder()
                    .paymentStatus(order.getPaymentStatus())
                    .status(order.getStatus())
                    .build();
        }
        return OrdersResponse.builder().build();
    }

    public void deleteOrder(int id) {

        ordersDAO.deleteById(id);
    }
}
