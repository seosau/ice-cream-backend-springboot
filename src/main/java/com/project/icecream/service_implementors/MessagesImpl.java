package com.project.icecream.service_implementors;

import com.project.icecream.models.Messages;
import com.project.icecream.repositories.MessagesDAO;
import com.project.icecream.services.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessagesImpl implements MessagesService {
    @Autowired
    private MessagesDAO messagesDAO;

    @Override
    public List<Messages> getMessages() {
        return messagesDAO.findAll();
    }
}
