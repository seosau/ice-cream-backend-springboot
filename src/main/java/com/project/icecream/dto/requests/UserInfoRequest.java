package com.project.icecream.dto.requests;

import com.project.icecream.models.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoRequest {
    private int id;
    private String name;
    private String email;
    private String image;
    private String user_type;
    private String old_password;
    private String password;
    private String password_confirmation;

    public UserInfoRequest (Users user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.image = user.getImage();
        this.user_type = user.getUser_type();
    }
    public UserInfoRequest (int id, String name, String email, String user_type){
        this.id = id;
        this.name = name;
        this.email = email;
        this.user_type = user_type;
    }
}
