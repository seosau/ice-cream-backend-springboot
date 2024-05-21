package com.project.icecream.services;

import org.springframework.stereotype.Service;

@Service
public interface DashboardService {
    public long getTotalMessages();
    public long getTotalProducts();
    public long getTotalActiveProducts();
    public long getTotalInactiveProducts();
    public long getTotalUserAccounts();
    public long getTotalSellerAccounts();
    public long getTotalOrderPlaced();
    public long getTotalOrderCanceled();
    public long getTotalOrderConfirmed();

}
