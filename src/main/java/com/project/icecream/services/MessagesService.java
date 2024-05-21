package com.project.icecream.services;

import com.project.icecream.models.Messages;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessagesService {
    List <Messages> getMessages();
}
