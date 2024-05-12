package com.project.icecream.service_implementors;

import com.project.icecream.models.Admins;
import com.project.icecream.models.Users;
import com.project.icecream.repositories.AdminDAO;
import com.project.icecream.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
//@Component
@Service
public class AdminsImpl implements UsersService {

    @Autowired
    private AdminDAO adminDAO;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public List<Users> getAllUser() {
        List<Admins> adminsList = adminDAO.findAll();
        List<Users> usersList = new ArrayList<>(adminsList);
        return usersList;
    }

    @Override
    public void saveUser(Users user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        Admins admins = new Admins(user);
        adminDAO.save(admins);
    }

    @Override
    public boolean isEmailRegistered(String email) {
        return adminDAO.findByEmail(email) != null;
    }

    @Override
    public Users isEmailAndPasswordCorrect(String email, String enteredPassword) {
        Admins storedUser = adminDAO.findByEmail(email);
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
