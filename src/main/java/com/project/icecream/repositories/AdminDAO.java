package com.project.icecream.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.icecream.models.Admins;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminDAO extends JpaRepository<Admins, Long>  {
    Admins findByEmail(String email);
    Admins findByEmailAndPassword(String email, String password);
}
