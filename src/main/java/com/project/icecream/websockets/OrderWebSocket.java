package com.project.icecream.websockets;

import com.project.icecream.models.Orders;
import com.project.icecream.models.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class OrderWebSocket {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @MessageMapping("/updateOrder")
    public void sendOrderUpdate(@Payload Orders order) {
        messagingTemplate.convertAndSend("/topic/orderUpdates", order);
        System.out.println(order);
    }
}
