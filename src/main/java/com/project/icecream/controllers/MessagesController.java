package com.project.icecream.controllers;

import com.project.icecream.dto.requests.MessageRequest;
import com.project.icecream.models.Messages;
import com.project.icecream.service_implementors.MessagesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class MessagesController {
    @Autowired
    private MessagesImpl messagesService;
    @GetMapping({"/seller/message", "/admin/message"})
    public ResponseEntity<?> getMessages() {
        List<Messages> messages = messagesService.getMessages();
        return ResponseEntity.ok(messages);
    }
    @PostMapping({"/message"})
    public ResponseEntity<?> sendMessage(@RequestBody MessageRequest requestBody) {
        messagesService.sendMessage(requestBody);
        return ResponseEntity.ok("Gui feedback thanh cong");
    }
}
