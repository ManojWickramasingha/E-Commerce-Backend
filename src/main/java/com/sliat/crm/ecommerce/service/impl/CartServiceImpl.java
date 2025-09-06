package com.sliat.crm.ecommerce.service.impl;

import com.sliat.crm.ecommerce.configuration.JwtRequestFilter;
import com.sliat.crm.ecommerce.dao.CartDao;
import com.sliat.crm.ecommerce.dao.ProductDao;
import com.sliat.crm.ecommerce.dao.UserDao;
import com.sliat.crm.ecommerce.entity.Cart;
import com.sliat.crm.ecommerce.entity.Product;
import com.sliat.crm.ecommerce.entity.User;
import com.sliat.crm.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final ProductDao productDao;
    private final UserDao userDao;
    private final CartDao cartDao;
    private final JwtRequestFilter jwtRequestFilter;

    @Override
    public Cart addCart(Integer productId) {
        Cart save = null;
        Product product = productDao.findById(productId).orElse(null);
        String currentUser = jwtRequestFilter.getCurrentUser();
        User user = userDao.findById(currentUser).orElse(null);


        if (product != null && user != null) {
            List<Cart> byUserCartList = cartDao.findByUser(user);
            List<Cart> filteredList = byUserCartList.stream().filter(x -> x.getProduct().getId().equals(productId)).collect(Collectors.toList());
            if (filteredList.isEmpty())
                return null;

            Cart newCart = new Cart();
            newCart.setUser(user);
            newCart.setProduct(product);

            save = cartDao.save(newCart);
        }

        return save;
    }

    public List<Cart> getCartDetails() {
        String currentUser = jwtRequestFilter.getCurrentUser();
        User user = userDao.findById(currentUser).orElse(null);

        if (user != null)
            return cartDao.findByUser(user);

        return new ArrayList<>();
    }

    @Override
    public void deleteCartItem(Integer cartId) {
        cartDao.deleteById(cartId);
    }
}
