package com.project.icecream.utils;

import static com.project.icecream.utils.JwtTokenUtil.getUserIdFromToken;

public class GetIdFromTokenHeader {
    public  static int getUserIdFromTokenHeader(String tokenHeader){
        String token = tokenHeader.substring(7);
        return getUserIdFromToken(token);
    }
}
