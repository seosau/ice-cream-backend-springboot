package com.project.icecream.controllers;

import com.project.icecream.dto.UserLoginResponse;
import com.project.icecream.models.Users;
import com.project.icecream.service_implementors.UsersImpl;
import com.project.icecream.utils.JwtTokenUtil;
import com.project.icecream.utils.SaveImageToFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import  java.util.List;

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
            System.out.println(user);
            if (user.getEmail() == null || user.getEmail().isEmpty()) {
                return ResponseEntity.badRequest().body("Email không được trống");
            }
            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                return ResponseEntity.badRequest().body("Mật khẩu không được trống");
            }
            Users loginUser = usersService.isEmailAndPasswordCorrect(user.getEmail(), user.getPassword());
            if (loginUser != null) {
                if(loginUser.getUser_type().equals("client")){
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
            if (user.getEmail() == null || user.getEmail().isEmpty()) {
                return ResponseEntity.badRequest().body("Email không được trống");
            }
            if (user.getName() == null || user.getName().isEmpty()) {
                return ResponseEntity.badRequest().body("Tên không được trống");
            }
            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                return ResponseEntity.badRequest().body("Mật khẩu không được trống");
            }
            if (user.getImage() == null || user.getImage().isEmpty()) {
                return ResponseEntity.badRequest().body("Ảnh không được trống");
            }
            if (usersService.isEmailRegistered(user.getEmail())) {
                return ResponseEntity.badRequest().body("Email này đã được đăng ký rồi");
            }
            user.setUser_type("client");
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
            System.out.println(user);
            if (user.getEmail() == null || user.getEmail().isEmpty()) {
                return ResponseEntity.badRequest().body("Email không được trống");
            }
            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                return ResponseEntity.badRequest().body("Mật khẩu không được trống");
            }
            Users loginUser = usersService.isEmailAndPasswordCorrect(user.getEmail(), user.getPassword());
            if (loginUser != null) {
                if(loginUser.getUser_type().equals("seller")){
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
            if (user.getEmail() == null || user.getEmail().isEmpty()) {
                return ResponseEntity.badRequest().body("Email không được trống");
            }
            if (user.getName() == null || user.getName().isEmpty()) {
                return ResponseEntity.badRequest().body("Tên không được trống");
            }
            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                return ResponseEntity.badRequest().body("Mật khẩu không được trống");
            }
            if (user.getImage() == null || user.getImage().isEmpty()) {
                return ResponseEntity.badRequest().body("Ảnh không được trống");
            }
            if (usersService.isEmailRegistered(user.getEmail())) {
                return ResponseEntity.badRequest().body("Email này đã được đăng ký rồi");
            }
            user.setUser_type("seller");
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
            System.out.println(user);
            if (user.getEmail() == null || user.getEmail().isEmpty()) {
                return ResponseEntity.badRequest().body("Email không được trống");
            }
            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                return ResponseEntity.badRequest().body("Mật khẩu không được trống");
            }
            Users loginUser = usersService.isEmailAndPasswordCorrect(user.getEmail(), user.getPassword());
            if (loginUser != null) {
                if(loginUser.getUser_type().equals("admin")){
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
            if (user.getEmail() == null || user.getEmail().isEmpty()) {
                return ResponseEntity.badRequest().body("Email không được trống");
            }
            if (user.getName() == null || user.getName().isEmpty()) {
                return ResponseEntity.badRequest().body("Tên không được trống");
            }
            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                return ResponseEntity.badRequest().body("Mật khẩu không được trống");
            }
            if (user.getImage() == null || user.getImage().isEmpty()) {
                return ResponseEntity.badRequest().body("Ảnh không được trống");
            }
            if (usersService.isEmailRegistered(user.getEmail())) {
                return ResponseEntity.badRequest().body("Email này đã được đăng ký rồi");
            }
            user.setUser_type("admin");
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
