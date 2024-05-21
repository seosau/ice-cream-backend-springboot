package com.project.icecream.controllers;

import com.project.icecream.dto.responses.DashboardResponse;
import com.project.icecream.service_implementors.DashboardImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class DashboardController {
    @Autowired
    private DashboardImpl dashboardService;
    @GetMapping("/seller/dashboard")
    public ResponseEntity<?> getSellerDashboard() {
        DashboardResponse dashboardResponse = DashboardResponse.builder()
                .totalMessages(dashboardService.getTotalMessages())
                .totalOrderConfirmed(dashboardService.getTotalOrderConfirmed())
                .totalActiveProducts(dashboardService.getTotalActiveProducts())
                .totalInactiveProducts(dashboardService.getTotalInactiveProducts())
                .totalOrderCanceled(dashboardService.getTotalOrderCanceled())
                .totalOrderPlaced(dashboardService.getTotalOrderPlaced())
                .totalProducts(dashboardService.getTotalProducts())
                .build();
        return ResponseEntity.ok(dashboardResponse);
    }
    @GetMapping("/admin/dashboard")
    public ResponseEntity<?> getAdminDashboard() {
        DashboardResponse dashboardResponse = DashboardResponse.builder()
                .totalMessages(dashboardService.getTotalMessages())
                .totalOrderConfirmed(dashboardService.getTotalOrderConfirmed())
                .totalActiveProducts(dashboardService.getTotalActiveProducts())
                .totalInactiveProducts(dashboardService.getTotalInactiveProducts())
                .totalOrderCanceled(dashboardService.getTotalOrderCanceled())
                .totalOrderPlaced(dashboardService.getTotalOrderPlaced())
                .totalProducts(dashboardService.getTotalProducts())
                .totalUserAccounts(dashboardService.getTotalUserAccounts())
                .totalSellerAccounts(dashboardService.getTotalSellerAccounts())
                .build();
        return ResponseEntity.ok(dashboardResponse);
    }

}
