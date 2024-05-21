package com.project.icecream.dto.responses;

import com.project.icecream.models.Users;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginResponse {
    private Users user;
    private String token;

    public UserLoginResponse(Users user, String token){
        this.user = user;
        this.token = token;
    }
}
