package com.project.icecream.services;

import com.project.icecream.models.Users;
import org.springframework.stereotype.Service;

import  java.util.List;
@Service
public interface UsersService {
    public List<Users> getAllUser();
    public void saveUser(Users user);
    public boolean isEmailRegistered(String email);
    public Users isEmailAndPasswordCorrect(String email, String password);
    public boolean authenticate (String enteredPassword, String storedPassword);
}
