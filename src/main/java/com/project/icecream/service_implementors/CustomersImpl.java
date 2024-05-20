package com.project.icecream.service_implementors;

import com.project.icecream.dto.responses.UsersResponse;
import com.project.icecream.models.Customers;
import com.project.icecream.models.Users;
import com.project.icecream.repositories.CustomerDAO;
import com.project.icecream.repositories.OrdersDAO;
import com.project.icecream.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
//@Component
@Service
public class CustomersImpl implements UsersService {

    @Autowired
    private CustomerDAO customerDAO;
    @Autowired
    private OrdersDAO ordersDAO;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public List<Users> getAllUser() {
        List<Customers> customersList = customerDAO.findAll();
        List<Users> usersList = new ArrayList<>(customersList);
        return usersList;
    }

    public List<UsersResponse> getAllUserByAdmin() {
        List<Customers> customersList = customerDAO.findAll();
        List<UsersResponse> usersList = new ArrayList<>();

        for (Customers cus : customersList) {
            int orderQuantity = ordersDAO.countByUserId(cus.getId());
            UsersResponse user = UsersResponse.builder()
                    .users(cus)
                    .orderQuantity(orderQuantity)
                    .build();
            usersList.add(user);
        }
        return usersList;
    }

    @Override
    public void saveUser(Users user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        Customers customers = new Customers(user);
        customerDAO.save(customers);
    }

    @Override
    public boolean isEmailRegistered(String email) {
        return customerDAO.findByEmail(email) != null;
    }

    @Override
    public Users isEmailAndPasswordCorrect(String email, String enteredPassword) {
        Customers storedUser = customerDAO.findByEmail(email);
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
