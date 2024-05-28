package com.project.icecream.service_implementors;

import com.project.icecream.models.Products;
import com.project.icecream.repositories.*;
import com.project.icecream.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DashboardImpl implements DashboardService {
    @Autowired
    private ProductsDAO productsDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private MessagesDAO messagesDAO;
    @Autowired
    private OrdersDAO ordersDAO;
    public long getTotalMessages() {
        return messagesDAO.count();
    }
    public long getTotalProducts() {
        return productsDAO.count();
    }
    public long getTotalActiveProducts() {
        return productsDAO.countByStatus("đang bán");
    }
    public long getTotalInactiveProducts() {
        return productsDAO.countByStatus("chưa bán");
    }
    public long getTotalUserAccounts() {
        return userDAO.countByUserType("client");
    }
    public long getTotalSellerAccounts() {
        return userDAO.countByUserType("seller");
    }
    public long getTotalOrderPlaced() {
        return ordersDAO.count();
    }
    public long getTotalOrderCanceled() {
        return ordersDAO.countByStatusAndPaymentStatus("Đã hủy", "Đang chờ");
    }
    public long getTotalOrderConfirmed() {
        return ordersDAO.countByStatusAndPaymentStatus("Đã giao", "Hoàn thành");
    }
}
