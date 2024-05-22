package com.project.icecream.utils;

import com.project.icecream.dto.requests.UserInfoRequest;
import com.project.icecream.dto.responses.UserInfoResponse;
import com.project.icecream.models.Users;

public class AddHostUrl {
    private static final String HOST_URL = "http://localhost:8080/api/";
    public static Users addHostUrlForImage(Users user) {
        user.setImage(HOST_URL + user.getImage());
        return user;
    }
    public static UserInfoResponse addHostUrlForImage(UserInfoResponse user) {
        user.setImage_url(HOST_URL + user.getImage_url());
        return user;
    }
    public static UserInfoRequest addHostUrlForImage(UserInfoRequest user) {
        user.setImage(HOST_URL + user.getImage());
        return user;
    }
}
