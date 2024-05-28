package com.project.icecream.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.icecream.models.Users;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDAO extends JpaRepository<Users, Integer>  {
    Users findByEmail(String email);
    long countByUserType(String userType);
    Users findByEmailAndPassword(String email, String password);
//    List<Users> findByUser_type(String userType);
}
