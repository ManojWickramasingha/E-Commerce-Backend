package com.sliat.crm.ecommerce.dao;

import com.sliat.crm.ecommerce.entity.Cart;
import com.sliat.crm.ecommerce.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartDao extends CrudRepository<Cart, Integer> {

    List<Cart> findByUser(User user);
}
