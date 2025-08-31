package com.sliat.crm.ecommerce.dao;

import com.sliat.crm.ecommerce.entity.Product;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductDao extends CrudRepository<Product, Integer> {
    Iterable<Object> findAll(Pageable pageRequest);

    List<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String searchKey1, String searchKey2, Pageable pageable);




}
