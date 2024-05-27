package com.project.icecream.service_implementors;

import com.project.icecream.dto.requests.OrdersRequest;
import com.project.icecream.dto.requests.PlaceOrderRequest;
import com.project.icecream.dto.responses.CartsResponse;
import com.project.icecream.dto.responses.OrderListByIdResponse;
import com.project.icecream.dto.responses.OrderListResponse;
import com.project.icecream.dto.responses.OrdersResponse;
import com.project.icecream.models.Users;
import com.project.icecream.models.Orders;
import com.project.icecream.models.Products;
import com.project.icecream.models.Users;
import com.project.icecream.repositories.CartsDAO;
import com.project.icecream.repositories.UserDAO;
import com.project.icecream.repositories.OrdersDAO;
import com.project.icecream.repositories.ProductsDAO;
import com.project.icecream.services.OrdersService;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.project.icecream.utils.AddHostUrl.addHostUrlForImage;
import static com.project.icecream.utils.GetIdFromTokenHeader.getUserIdFromTokenHeader;

@Service
public class OrdersImpl implements OrdersService {
    @Autowired
    private OrdersDAO ordersDAO;

    @Autowired
    private ProductsDAO productsDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private CartsDAO cartsDAO;

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

    @Override
    @Transactional
    public void placeOrder (PlaceOrderRequest placeOrderRequest){
        for (CartsResponse productRequest : placeOrderRequest.getProducts()){
            Orders orderStore = new Orders(placeOrderRequest, productRequest);
            ordersDAO.save(orderStore);
            //Giảm số lượng tồn kho
            Optional<Products> inDBProduct = productsDAO.findById(productRequest.getId());
            if(inDBProduct.isPresent()){
                Products product = inDBProduct.get();
                product.setStock(product.getStock() - productRequest.getQuantity());
                productsDAO.save(product);
            }
        }
        cartsDAO.deleteAllByUserId(placeOrderRequest.getUser_id());
    }

    @Override
    public String rePlaceOrder (int oderId, PlaceOrderRequest placeOrderRequest){
        for (CartsResponse productRequest : placeOrderRequest.getProducts()) {
            Orders orderRequesting = new Orders(placeOrderRequest,productRequest);
            orderRequesting.setId(oderId);
            ordersDAO.save(orderRequesting);
//            Optional<Orders> orderStored = ordersDAO.findById(oderId);
//            if (orderStored.isPresent()) {
//
//            }
        }
        return "Đặt lại thành công";
    }

    @Override
    public OrderListResponse getClientOrder (String tokenHeader) {
        List<Orders> ordersList = ordersDAO.findByUserId(getUserIdFromTokenHeader(tokenHeader));
        List<OrdersResponse> ordersResponseList = new ArrayList<>();
        for (Orders order: ordersList) {
            Optional<Products> product = productsDAO.findById(order.getProductId());
            if (product.isPresent()){
                ordersResponseList.add(OrdersResponse.builder()
                        .id(order.getId())
                        .productName(product.get().getName())
                        .userName(order.getName())
                        .quantity(order.getQuantity())
                        .price(order.getPrice())
                        .date(order.getCreatedAt())
                        .phoneNumber(order.getPhoneNumber())
                        .email(order.getEmail())
                        .paymentMethod(order.getPaymentMethod())
                        .address(order.getAddress())
                        .status(order.getStatus())
                        .paymentStatus(order.getPaymentStatus())
                        .imageUrl(addHostUrlForImage(product.get().getImage()))
                        .build()
                );
            }
        }
        return new OrderListResponse(ordersResponseList);
    }

    @Override
    public List<OrderListByIdResponse> getOrderByOrderId(int orderId){
        Optional<Orders> order = ordersDAO.findById(orderId);
        if (order.isPresent()){
            Optional<Products> product = productsDAO.findById(order.get().getProductId());
            if(product.isPresent()) {
                Products productResponse = product.get();
                productResponse.setImage(addHostUrlForImage(productResponse.getImage()));
                List<OrderListByIdResponse> orderListByIdResponses = new ArrayList<>();
                orderListByIdResponses.add(new OrderListByIdResponse(-1,order.get().getProductId(), order.get().getQuantity(), productResponse));
                return orderListByIdResponses;
            }
        }
        return null;
    }

    @Override
    public List<OrderListByIdResponse> getOrderByProductId (int productId) {
        Optional<Products> product = productsDAO.findById(productId);
        if (product.isPresent()){
            Products productResponse = product.get();
            productResponse.setImage(addHostUrlForImage(productResponse.getImage()));
            List<OrderListByIdResponse> orderListByIdResponses = new ArrayList<>();
            orderListByIdResponses.add(new OrderListByIdResponse(-1,productResponse.getId(), 1, productResponse));
            return orderListByIdResponses;
        }
        return null;
    }

    @Override
    public OrdersResponse getOrderDetailById (int orderId){
        Optional<Orders> orderStored = ordersDAO.findById(orderId);
        if (orderStored.isPresent()){
            Optional<Products> productRef = productsDAO.findById(orderStored.get().getProductId());
            if(productRef.isPresent()) {
                Products productRes = productRef.get();
                productRes.setImage(addHostUrlForImage(productRes.getImage()));
                return OrdersResponse.builder()
                        .id(orderStored.get().getId())
                        .productName(productRes.getName())
                        .userName(orderStored.get().getName())
                        .quantity(orderStored.get().getQuantity())
                        .price(orderStored.get().getPrice())
                        .date(orderStored.get().getCreatedAt())
                        .phoneNumber(orderStored.get().getPhoneNumber())
                        .email(orderStored.get().getEmail())
                        .paymentMethod(orderStored.get().getPaymentMethod())
                        .address(orderStored.get().getAddress())
                        .status(orderStored.get().getStatus())
                        .paymentStatus(orderStored.get().getPaymentStatus())
                        .imageUrl(productRes.getImage())
                        .build();
            }
        }
        return null;
    }

    @Override
    public boolean cancelOrder (int orderId) {
        Optional<Orders> orderStored = ordersDAO.findById(orderId);
        if (orderStored.isPresent()) {
            Orders order = orderStored.get();
            order.setStatus("đã hủy");
            ordersDAO.save(order);
            return true;
        }
        return false;
    }
}
