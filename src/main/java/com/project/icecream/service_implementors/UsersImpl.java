package com.project.icecream.service_implementors;

import com.project.icecream.dto.responses.UsersResponse;
import com.project.icecream.models.Users;
import com.project.icecream.repositories.UserDAO;
import com.project.icecream.repositories.OrdersDAO;
import com.project.icecream.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
//@Component
@Service
public class UsersImpl implements UsersService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private OrdersDAO ordersDAO;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public List<Users> getAllUser() {
        return userDAO.findAll();
    }

    @Override
    public void saveUser(Users user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userDAO.save(user);
    }

    @Override
    public boolean isEmailRegistered(String email) {
        return userDAO.findByEmail(email) != null;
    }

    @Override
    public Users isEmailAndPasswordCorrect(String email, String enteredPassword) {
        Users storedUser = userDAO.findByEmail(email);
        if (storedUser != null && authenticate(enteredPassword, storedUser.getPassword())){
            storedUser.setImage("http://localhost:8080/api/" + storedUser.getImage());
            return storedUser;
        }
        return null;
    }

    @Override
    public boolean authenticate(String enteredPassword, String storedPassword) {
        return passwordEncoder.matches(enteredPassword, storedPassword);
    }

    public List<UsersResponse> getAllSellerByAdmin() {
        List<Users> sellersList = userDAO.findAll();
        List<UsersResponse> usersList = new ArrayList<>();

        for (Users seller : sellersList) {
            UsersResponse user = UsersResponse.builder()
                    .users(seller)
                    .build();
            usersList.add(user);
        }
        return usersList;
    }

    public List<UsersResponse> getAllUserByAdmin() {
        List<Users> customersList = userDAO.findAll();
        List<UsersResponse> usersList = new ArrayList<>();

        for (Users cus : customersList) {
            int orderQuantity = ordersDAO.countByUserId(cus.getId());
            UsersResponse user = UsersResponse.builder()
                    .users(cus)
                    .orderQuantity(orderQuantity)
                    .build();
            usersList.add(user);
        }
        return usersList;
    }
}
