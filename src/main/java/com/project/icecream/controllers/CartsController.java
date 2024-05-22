package com.project.icecream.controllers;

import com.project.icecream.dto.requests.CartRequest;
import com.project.icecream.dto.responses.CartsResponse;
import com.project.icecream.models.Carts;
import com.project.icecream.service_implementors.CartsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class CartsController {
    @Autowired
    private CartsImpl cartsService;
    @PostMapping({"/cart"})
    public ResponseEntity<?> addToCarts(@RequestBody CartRequest requestBody) {
        return ResponseEntity.ok(cartsService.addToCart(requestBody));
    }
    @GetMapping({"/cart/{userId}"})
    public ResponseEntity<?> getCarts(@PathVariable int userId) {
        List<CartsResponse> carts = cartsService.getCarts(userId);
        return ResponseEntity.ok(carts);
    }
    @GetMapping({"/quantityCartItems/{userId}"})
    public ResponseEntity<?> getCartsId(@PathVariable int userId) {
        List<CartsResponse> carts = cartsService.getCartsId(userId);
        return ResponseEntity.ok(carts);
    }
    @DeleteMapping({"/cart/{id}/{userId}"})
    public ResponseEntity<?> deleteItem(@PathVariable int id, @PathVariable int userId) {
        return ResponseEntity.ok(cartsService.deleteItem(id, userId));
    }
    @PutMapping({"/cart/{id}/{userId}"})
    public ResponseEntity<?> changeQuantity(@PathVariable int id, @PathVariable int userId, @RequestBody CartRequest requestBody) {
        return ResponseEntity.ok(cartsService.changeQuantity(id, userId, requestBody));
    }
}
