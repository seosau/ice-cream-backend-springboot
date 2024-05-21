package com.project.icecream.repositories;

import com.project.icecream.models.Messages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessagesDAO extends JpaRepository<Messages, Integer> {
}
