package com.sliat.crm.ecommerce.dao;

import com.sliat.crm.ecommerce.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductDao extends CrudRepository<Product, Integer> {
}
