package com.project.icecream.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.icecream.models.Users;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends JpaRepository<Users, Long>  {
    Users findByEmail(String email);
    Users findByEmailAndPassword(String email, String password);
}
