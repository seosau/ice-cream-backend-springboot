package com.project.icecream.controllers;

import com.project.icecream.dto.responses.UserLoginResponse;
import com.project.icecream.enums.Role;
import com.project.icecream.models.Users;
import com.project.icecream.service_implementors.UsersImpl;
import com.project.icecream.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import  java.util.List;

import static com.project.icecream.utils.GenerateSlugUtil.convertToSlug;
import static com.project.icecream.utils.SaveImageBase64Util.saveBase64ImageToFile;

@RestController
public class UsersController {

    @Autowired
    private UsersImpl usersService;

    @GetMapping("")
    public ResponseEntity<?> getListUser(){
        List<Users> users = usersService.getAllUser();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/login")
    public ResponseEntity<?> customerLogin(@RequestBody Users user){
        try {
            Users loginUser = usersService.isEmailAndPasswordCorrect(user.getEmail(), user.getPassword());
            if (loginUser != null) {
                if(loginUser.getUser_type().equals(Role.client.name())){
                    String token = JwtTokenUtil.generateToken(loginUser);
                    return ResponseEntity.ok().body(new UserLoginResponse(loginUser, token));
                }
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                        .body("Email hoặc mật khẩu không chính xác");
            }
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body("Email hoặc mật khẩu không chính xác");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi đăng nhập" + ex.getMessage());
        }
    }
    @PostMapping("/register")
    public  ResponseEntity<?> customerRegister(@RequestBody Users user){
        try {
            if (usersService.isEmailRegistered(user.getEmail())) {
                return ResponseEntity.badRequest().body("Email này đã được đăng ký rồi");
            }
            user.setImage(saveBase64ImageToFile(user.getImage(), convertToSlug(user.getEmail())));
            user.setUser_type(Role.client.name());
            usersService.saveUser(user);
            return ResponseEntity.ok().body("Đăng ký thành công");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đăng ký thất bại: " + ex.getMessage());
        }
    }


    @PostMapping("/getlogout")
    public ResponseEntity<?> customerLogout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/seller/login")
    public ResponseEntity<?> sellerLogin(@RequestBody Users user){
        try {
            Users loginUser = usersService.isEmailAndPasswordCorrect(user.getEmail(), user.getPassword());
            if (loginUser != null) {
                if(loginUser.getUser_type().equals(Role.seller.name())){
                    String token = JwtTokenUtil.generateToken(loginUser);
                    return ResponseEntity.ok().body(new UserLoginResponse(loginUser, token));
                }
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                        .body("Email hoặc mật khẩu không chính xác");
            }
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body("Email hoặc mật khẩu không chính xác");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi đăng nhập" + ex.getMessage());
        }
    }
    @PostMapping("/seller/register")
    public  ResponseEntity<?> sellerRegister(@RequestBody Users user){
        try {
            if (usersService.isEmailRegistered(user.getEmail())) {
                return ResponseEntity.badRequest().body("Email này đã được đăng ký rồi");
            }
            user.setImage(saveBase64ImageToFile(user.getImage(), convertToSlug(user.getEmail())));
            user.setUser_type(Role.seller.name());
            usersService.saveUser(user);
            return ResponseEntity.ok().body("Đăng ký thành công");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đăng ký thất bại: " + ex.getMessage());
        }
    }

    @PostMapping("/seller/logout")
    public ResponseEntity<?> sellerLogout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/admin/login")
    public ResponseEntity<?> adminLogin(@RequestBody Users user){
        try {
            Users loginUser = usersService.isEmailAndPasswordCorrect(user.getEmail(), user.getPassword());
            if (loginUser != null) {
                if(loginUser.getUser_type().equals(Role.admin.name())){
                    String token = JwtTokenUtil.generateToken(loginUser);
                    return ResponseEntity.ok().body(new UserLoginResponse(loginUser, token));
                }
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                        .body("Email hoặc mật khẩu không chính xác");
            }
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body("Email hoặc mật khẩu không chính xác");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi đăng nhập" + ex.getMessage());
        }
    }
    @PostMapping("/admin/register")
    public  ResponseEntity<?> adminRegister(@RequestBody Users user){
        try {
            if (usersService.isEmailRegistered(user.getEmail())) {
                return ResponseEntity.badRequest().body("Email này đã được đăng ký rồi");
            }
            user.setImage(saveBase64ImageToFile(user.getImage(), convertToSlug(user.getEmail())));
            user.setUser_type(Role.admin.name());
            usersService.saveUser(user);
            return ResponseEntity.ok().body("Đăng ký thành công");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đăng ký thất bại: " + ex.getMessage());
        }
    }

    @PostMapping("/admin/logout")
    public ResponseEntity<?> adminLogout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().build();
    }
}
