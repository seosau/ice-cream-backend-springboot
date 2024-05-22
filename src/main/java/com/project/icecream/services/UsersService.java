package com.project.icecream.services;

import com.project.icecream.dto.requests.UserInfoRequest;
import com.project.icecream.dto.responses.UserInfoResponse;
import com.project.icecream.models.Users;
import org.springframework.stereotype.Service;

import java.io.IOException;
import  java.util.List;
import java.util.Optional;

@Service
public interface UsersService {
    public List<Users> getAllUser();
    public void saveUser(Users user);
    public boolean isEmailRegistered(String email);
    public Users isEmailAndPasswordCorrect(String email, String password);
    public boolean authenticate (String enteredPassword, String storedPassword);
    public Users findById(int userId);
    public Users updateUserProfile(String tokenHeader, UserInfoRequest userNewInfo) throws IOException;
    public void countOrdersAndMessages(UserInfoResponse userInfoResponse);
}
