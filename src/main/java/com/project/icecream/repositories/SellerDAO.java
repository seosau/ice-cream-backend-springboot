package com.project.icecream.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.icecream.models.Sellers;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerDAO extends JpaRepository<Sellers, Long>  {
    Sellers findByEmail(String email);
    Sellers findByEmailAndPassword(String email, String password);
}
