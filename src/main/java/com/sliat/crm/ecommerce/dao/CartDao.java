package com.sliat.crm.ecommerce.dao;

import com.sliat.crm.ecommerce.entity.Cart;
import org.springframework.data.repository.CrudRepository;

public interface CartDao extends CrudRepository<Cart, Integer> {
}
