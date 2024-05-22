package com.project.icecream.repositories;

import com.project.icecream.models.Messages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessagesDAO extends JpaRepository<Messages, Integer> {
    public List<Messages> findByUserId(int userId);
}
