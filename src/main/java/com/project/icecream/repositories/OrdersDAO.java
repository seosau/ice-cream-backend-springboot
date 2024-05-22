package com.project.icecream.repositories;

import com.project.icecream.models.Messages;
import com.project.icecream.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersDAO extends JpaRepository<Orders, Integer> {
    int countByUserId(int id);
    long countByStatusAndPaymentStatus(String status, String paymentStatus);
    List<Orders> findByStatusAndPaymentStatus(String status, String paymentStatus);
    public List<Orders> findByUserId(int userId);
}
