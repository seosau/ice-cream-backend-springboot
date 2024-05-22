package com.project.icecream.service_implementors;

import com.project.icecream.dto.requests.UserInfoRequest;
import com.project.icecream.dto.responses.UserInfoResponse;
import com.project.icecream.dto.responses.UsersResponse;
import com.project.icecream.models.Users;
import com.project.icecream.repositories.MessagesDAO;
import com.project.icecream.repositories.UserDAO;
import com.project.icecream.repositories.OrdersDAO;
import com.project.icecream.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.project.icecream.utils.AddHostUrl.addHostUrlForImage;
import static com.project.icecream.utils.GenerateSlugUtil.convertToSlug;
import static com.project.icecream.utils.JwtTokenUtil.getUserInfoFromTokenHeader;
import static com.project.icecream.utils.SaveImageBase64Util.saveBase64ImageToFile;

//@Component
@Service
public class UsersImpl implements UsersService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private OrdersDAO ordersDAO;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MessagesDAO messagesDAO;
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

    @Override
    public Users findById(int userId){
        Optional<Users> optionalUser = userDAO.findById(userId);
        Users user = optionalUser.get();
        //Them duong dan cho anh
        addHostUrlForImage(user);
        return user;
    }
    @Override
    public Users updateUserProfile(String tokenHeader, UserInfoRequest userNewInfo) throws IOException {
        UserInfoRequest userInfoRequest = getUserInfoFromTokenHeader(tokenHeader);
        Users userStored = isEmailAndPasswordCorrect(userInfoRequest.getEmail(), userNewInfo.getOld_password());
        if(userStored != null){
            if(!(userNewInfo.getName() == null || userNewInfo.getName().isBlank())){
                userStored.setName(userNewInfo.getName());
            }
            if(!(userNewInfo.getEmail() == null || userNewInfo.getEmail().isBlank())){
                userStored.setEmail(userNewInfo.getEmail());
            }
            if(!(userNewInfo.getImage() == null || userNewInfo.getImage().isBlank())){
                String imagePath = saveBase64ImageToFile(userNewInfo.getImage(),convertToSlug(userStored.getEmail()));
                userStored.setImage(imagePath);
            }
            if(!(userNewInfo.getPassword() == null || userNewInfo.getPassword().isBlank())){
                userStored.setPassword(passwordEncoder.encode(userNewInfo.getPassword()));
            }
            userDAO.save(userStored);

            //Them duong dan cho anh
            addHostUrlForImage(userStored);
            return  userStored;
        } else {
            return null;
        }
    }
    @Override
    public void countOrdersAndMessages(UserInfoResponse userInfoResponse){
        userInfoResponse.setTotalMessages(messagesDAO.findByUserId(userInfoResponse.getId()).size());
        userInfoResponse.setTotalOrders(ordersDAO.findByUserId(userInfoResponse.getId()).size());
    }
}
