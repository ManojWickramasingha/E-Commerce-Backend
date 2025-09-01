package com.sliat.crm.ecommerce.controller;

import com.sliat.crm.ecommerce.entity.Cart;
import com.sliat.crm.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add_cart/{productId}")
    @PreAuthorize("hasRole('user')")
    public ResponseEntity<Cart> addCart(@PathVariable(name = "productId") Integer productId) {
        Cart cart = cartService.addCart(productId);
        if (cart != null)
            return ResponseEntity.ok(cart);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}
