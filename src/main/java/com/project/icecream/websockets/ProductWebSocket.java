package com.project.icecream.websockets;

import com.project.icecream.models.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ProductWebSocket {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @MessageMapping("/updateProduct")
    public void sendProductUpdate(@Payload Products product) {
        messagingTemplate.convertAndSend("/topic/productUpdates", product);
        System.out.println(product);
    }
}
