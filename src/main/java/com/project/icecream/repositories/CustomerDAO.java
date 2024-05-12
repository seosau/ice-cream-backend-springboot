package com.project.icecream.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.icecream.models.Customers;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDAO extends JpaRepository<Customers, Long>  {
    Customers findByEmail(String email);
    Customers findByEmailAndPassword(String email, String password);
}
