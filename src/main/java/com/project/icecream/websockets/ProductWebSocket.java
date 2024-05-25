package com.project.icecream.websockets;

import com.project.icecream.models.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ProductWebSocket {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendProductUpdate(Products product) {
        messagingTemplate.convertAndSend("/topic/productUpdates", product);
        System.out.println(product);
    }
}
