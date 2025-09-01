package com.sliat.crm.ecommerce.service;

import com.sliat.crm.ecommerce.dto.ProductDto;

import java.util.List;

public interface ProductService {

    ProductDto createNewProduct(ProductDto productData);



    List<ProductDto> getAllProduct(Integer pageNumber, String searchKey);

    boolean deleteProductDetails(Integer id);


    ProductDto getProductDetailById(Integer productId);

    List<ProductDto> getProductDetail(boolean isSingleProductDetail, Integer productId);

}
