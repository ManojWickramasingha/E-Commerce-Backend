package com.sliat.crm.ecommerce.controller;

import com.sliat.crm.ecommerce.entity.Cart;
import com.sliat.crm.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class CartController {

    private final CartService cartService;

    @GetMapping("/add_cart/{productId}")
    @PreAuthorize("hasRole('user')")
    public ResponseEntity<Cart> addCart(@PathVariable(name = "productId") Integer productId) {
        Cart cart = cartService.addCart(productId);
        if (cart != null)
            return ResponseEntity.ok(cart);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @GetMapping("/getCartDetails")
    @PreAuthorize("hasRole('user')")
    public ResponseEntity<List<Cart>> getCartDetail() {
        List<Cart> cartDetails = cartService.getCartDetails();
        if (cartDetails != null)
            return ResponseEntity.ok(cartDetails);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PreAuthorize("hasRole('user')")
    @GetMapping("/deleteCartItem/{cartId}")
    public void deleteCartItem(@PathVariable(name = "cartId") Integer cartId) {
        cartService.deleteCartItem(cartId);
    }
}
