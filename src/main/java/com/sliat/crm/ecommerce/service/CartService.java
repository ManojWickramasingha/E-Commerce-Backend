package com.sliat.crm.ecommerce.service;

import com.sliat.crm.ecommerce.entity.Cart;

import java.util.List;

public interface CartService {

    Cart addCart(Integer productId);

    List<Cart> getCartDetails();

    void deleteCartItem(Integer cartId);
}
