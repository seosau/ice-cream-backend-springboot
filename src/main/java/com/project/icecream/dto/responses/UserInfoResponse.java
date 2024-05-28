package com.project.icecream.dto.responses;

import com.project.icecream.models.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {
    private int id;
    private String name;
    private String email;
    private String image_url;
    private String user_type;
    private int totalOrders;
    private int totalMessages;

    public UserInfoResponse (Users user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.image_url = user.getImage();
        this.user_type = user.getUserType();
    }
    public UserInfoResponse (int id, String name, String email, String user_type){
        this.id = id;
        this.name = name;
        this.email = email;
        this.user_type = user_type;
    }
}
