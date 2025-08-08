package com.sliat.crm.ecommerce.controller;

import com.sliat.crm.ecommerce.dto.ProductDto;
import com.sliat.crm.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/product")
@CrossOrigin
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDto> createNewProduct(@RequestBody ProductDto productData) {
        ProductDto product = productService.createNewProduct(productData);
        if (product != null)
            return ResponseEntity.ok(product);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

}
