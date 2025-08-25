package com.sliat.crm.ecommerce.controller;

import com.sliat.crm.ecommerce.dto.ProductDto;
import com.sliat.crm.ecommerce.entity.ImageModel;
import com.sliat.crm.ecommerce.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/admin/product")
@CrossOrigin
@Slf4j
public class ProductController {
    @Autowired
    private ProductService productService;

    @PreAuthorize("hasRole('admin')")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ProductDto> createNewProduct(@RequestPart("product") ProductDto productData, @RequestPart("images") MultipartFile[] file) {
        try {
            Set<ImageModel> imageModels = uploadImages(file);
            productData.setProductImages(imageModels);
            ProductDto product = productService.createNewProduct(productData);
            if (product != null)
                return ResponseEntity.ok(product);


        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    private Set<ImageModel> uploadImages(MultipartFile[] file) throws Exception {
        Set<ImageModel> images = new HashSet<>();

        for (MultipartFile f : file) {
            ImageModel imageModel =
                    new ImageModel(f.getOriginalFilename()
                            , f.getContentType(),
                            f.getBytes());

            images.add(imageModel);
        }

        return images;
    }


    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> getAllProduct() {
        List<ProductDto> allProduct = productService.getAllProduct();
        if (allProduct != null)
            return ResponseEntity.ok(allProduct);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @PreAuthorize("hasRole('admin')")
    @DeleteMapping("/deleteProductDetail/{productId}")
    public void deleteProductDetails(@PathVariable("productId") Integer id) {
        productService.deleteProductDetails(id);
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("getProductDetailById/{productId}")
    public ProductDto getProductDetailById(@PathVariable("productId") Integer productId) {
        ProductDto productDto = productService.getProductDetailById(productId);
        if (productDto != null)
            return productDto;

        return null;
    }

}
