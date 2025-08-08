package com.sliat.crm.ecommerce.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sliat.crm.ecommerce.dao.ProductDao;
import com.sliat.crm.ecommerce.dto.ProductDto;
import com.sliat.crm.ecommerce.entity.Product;
import com.sliat.crm.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ProductDao productDao;

    @Override
    public ProductDto createNewProduct(ProductDto productData) {
        Product product = mapper.convertValue(productData, Product.class);
        Product saveProduct = productDao.save(product);
        return mapper.convertValue(saveProduct, ProductDto.class);
    }
}
