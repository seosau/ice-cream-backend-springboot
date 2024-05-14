//package com.project.icecream.utils;
//
//import com.project.icecream.models.Users;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//
//public class JwtTokenUtil {
//    public static String generateToken(Users user){
//        String token = Jwts.builder()
//                .setSubject(user.getEmail())
////                .signWith(SignatureAlgorithm.HS512, "cH3lXJt1vPdulcO3cLjZgG9vxPHdWJuUJa7/rA6T1oJ5d6Mg5ISlqX3AOhN9yTlljZQWzoXHDa+x0VLJmdnEug==")
//                .signWith(Keys.hmacShaKeyFor("cH3lXJt1vPdulcO3cLjZgG9vxPHdWJuUJa7/rA6T1oJ5d6Mg5ISlqX3AOhN9yTlljZQWzoXHDa+x0VLJmdnEug==".getBytes()), SignatureAlgorithm.HS512)
//                .compact();
//        return token;
//    }
//}

package com.project.icecream.utils;

import com.project.icecream.models.Users;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {
    private static final String SECRET_KEY = "cH3lXJt1vPdulcO3cLjZgG9vxPHdWJuUJa7/rA6T1oJ5d6Mg5ISlqX3AOhN9yTlljZQWzoXHDa+x0VLJmdnEug==";
    private static final long EXPIRATION_TIME = 86400000;

    public static String generateToken(Users user){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        String token = Jwts.builder()
                .setSubject(user.getEmail())
                .claim("id", user.getId())
                .claim("name", user.getName())
                .claim("email", user.getEmail())
                .claim("user_type", user.getUser_type())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS512)
                .compact();
        return token;
    }
//    public int getUserIdFromToken(String token) {
//        Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
//        String userId = claims.getBody().getSubject();
//        return Integer.parseInt(userId);
//    }
    public static int getUserIdFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY.getBytes())
                    .parseClaimsJws(token)
                    .getBody();
            return Integer.parseInt(claims.get("id").toString());
        } catch (Exception e) {
            // Handle parsing or token validation errors
            return -1;
        }
    }
}