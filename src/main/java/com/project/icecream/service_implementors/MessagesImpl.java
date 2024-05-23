package com.project.icecream.service_implementors;

import com.project.icecream.dto.requests.MessageRequest;
import com.project.icecream.models.Messages;
import com.project.icecream.repositories.MessagesDAO;
import com.project.icecream.services.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessagesImpl implements MessagesService {
    @Autowired
    private MessagesDAO messagesDAO;

    @Override
    public List<Messages> getMessages() {
        return messagesDAO.findAll();
    }
    @Override
    public void sendMessage(MessageRequest requestBody) {
        LocalDateTime currentTime = LocalDateTime.now();
        Messages messages = Messages.builder()
                .userId(requestBody.getUserId())
                .userName(requestBody.getUserName())
                .subject(requestBody.getSubject())
                .message(requestBody.getMessage())
                .email(requestBody.getEmail())
                .createdAt(currentTime)
                .updatedAt(currentTime)
                .build();
        messagesDAO.save(messages);
    }
}
