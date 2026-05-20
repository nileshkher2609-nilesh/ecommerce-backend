package com.example.demoEcommmerce.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.demoEcommmerce.dto.CartItemRequest;
import com.example.demoEcommmerce.entity.CartItem;
import com.example.demoEcommmerce.service.CartItemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private CartItemService cartItemService;

    public CartController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping
    public ResponseEntity<String> addToCart(
            Authentication authentication,
            @Valid @RequestBody CartItemRequest request) {

        String email = authentication.getName();

        if (!cartItemService.addToCart(email, request)) {
            return ResponseEntity.badRequest()
                    .body("Not able to complete the request");
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeFromCart(
            Authentication authentication,
            @PathVariable Long productId) {

        String email = authentication.getName();

        boolean deleted =
                cartItemService.deleteItemFromCart(
                        email,
                        productId
                );

        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getCart(
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                cartItemService.getCart(email)
        );
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateQuantity(
    		@PathVariable Long id,
    		@RequestParam int quantity){
    	boolean updated = cartItemService.updateCartQuantity(id, quantity);

        if(updated) {
            return ResponseEntity.ok("Quantity updated");
        }

        return ResponseEntity.badRequest().body("Cart item not found");
    }
}