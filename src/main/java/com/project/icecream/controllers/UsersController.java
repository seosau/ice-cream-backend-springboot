package com.project.icecream.controllers;

import com.project.icecream.dto.UserLoginResponse;
import com.project.icecream.models.Users;
//import com.project.icecream.service_implementors.CustomersService;
import com.project.icecream.service_implementors.AdminsImpl;
import com.project.icecream.service_implementors.CustomersImpl;
import com.project.icecream.service_implementors.SellersImpl;
import com.project.icecream.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import  java.util.List;

@RestController
public class UsersController {
    @Autowired
    private CustomersImpl customersService;
    @Autowired
    private SellersImpl sellersService;
    @Autowired
    private AdminsImpl adminsService;

    @GetMapping("")
    public ResponseEntity<?> getListUser(){
        List<Users> users = customersService.getAllUser();
//        List<Users> users = new ArrayList<>();
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
            Users loginUser = customersService.isEmailAndPasswordCorrect(user.getEmail(), user.getPassword());
            if (loginUser != null) {
                String token = JwtTokenUtil.generateToken(loginUser);
                return ResponseEntity.ok().body(new UserLoginResponse(loginUser, token));
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
            if (customersService.isEmailRegistered(user.getEmail())) {
                return ResponseEntity.badRequest().body("Email này đã được đăng ký rồi");
            }
            user.setImage("123");
            user.setUser_type("client");
            customersService.saveUser(user);
            return ResponseEntity.ok().body("Đăng ký thành công");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đăng ký thất bại: " + ex.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> customerLogout() {
//        SecurityContextHolder.clearContext();
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
            Users loginUser = sellersService.isEmailAndPasswordCorrect(user.getEmail(), user.getPassword());
            if (loginUser != null) {
                String token = JwtTokenUtil.generateToken(loginUser);
                return ResponseEntity.ok().body(new UserLoginResponse(loginUser, token));
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
            if (sellersService.isEmailRegistered(user.getEmail())) {
                return ResponseEntity.badRequest().body("Email này đã được đăng ký rồi");
            }
            user.setUser_type("seller");
            sellersService.saveUser(user);
            return ResponseEntity.ok().body("Đăng ký thành công");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đăng ký thất bại: " + ex.getMessage());
        }
    }

    @PostMapping("/seller/logout")
    public ResponseEntity<?> sellerLogout() {
//        SecurityContextHolder.clearContext();
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
            Users loginUser = adminsService.isEmailAndPasswordCorrect(user.getEmail(), user.getPassword());
            if (loginUser != null) {
                String token = JwtTokenUtil.generateToken(loginUser);
                return ResponseEntity.ok().body(new UserLoginResponse(loginUser, token));
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
            if (adminsService.isEmailRegistered(user.getEmail())) {
                return ResponseEntity.badRequest().body("Email này đã được đăng ký rồi");
            }
            user.setUser_type("admin");
            adminsService.saveUser(user);
            return ResponseEntity.ok().body("Đăng ký thành công");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đăng ký thất bại: " + ex.getMessage());
        }
    }

    @PostMapping("/admin/logout")
    public ResponseEntity<?> adminLogout() {
//        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().build();
    }
}
