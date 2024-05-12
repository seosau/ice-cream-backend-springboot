package com.project.icecream.service_implementors;

import com.project.icecream.models.Sellers;
import com.project.icecream.models.Users;
import com.project.icecream.repositories.SellerDAO;
import com.project.icecream.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
//@Component
@Service
public class SellersImpl implements UsersService {

    @Autowired
    private SellerDAO sellerDAO;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public List<Users> getAllUser() {
        List<Sellers> sellersList = sellerDAO.findAll();
        List<Users> usersList = new ArrayList<>(sellersList);
        return usersList;
    }

    @Override
    public void saveUser(Users user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        Sellers sellers = new Sellers(user);
        sellerDAO.save(sellers);
    }

    @Override
    public boolean isEmailRegistered(String email) {
        return sellerDAO.findByEmail(email) != null;
    }

    @Override
    public Users isEmailAndPasswordCorrect(String email, String enteredPassword) {
        Sellers storedUser = sellerDAO.findByEmail(email);
        if (storedUser != null && authenticate(enteredPassword, storedUser.getPassword())){
            return storedUser;
        }
        return null;
    }

    @Override
    public boolean authenticate(String enteredPassword, String storedPassword) {
        return passwordEncoder.matches(enteredPassword, storedPassword);
    }
}
