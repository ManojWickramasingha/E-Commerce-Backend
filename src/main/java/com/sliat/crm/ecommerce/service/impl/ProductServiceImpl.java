package com.sliat.crm.ecommerce.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sliat.crm.ecommerce.configuration.JwtRequestFilter;
import com.sliat.crm.ecommerce.dao.CartDao;
import com.sliat.crm.ecommerce.dao.ProductDao;
import com.sliat.crm.ecommerce.dao.UserDao;
import com.sliat.crm.ecommerce.dto.ProductDto;
import com.sliat.crm.ecommerce.entity.Cart;
import com.sliat.crm.ecommerce.entity.Product;
import com.sliat.crm.ecommerce.entity.User;
import com.sliat.crm.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {


    private final ObjectMapper mapper;


    private final ProductDao productDao;

    private final JwtRequestFilter jwtRequestFilter;

    private final UserDao userDao;

    private final CartDao cartDao;

    @Override
    public ProductDto createNewProduct(ProductDto productData) {
        Product product = mapper.convertValue(productData, Product.class);
        Product saveProduct = productDao.save(product);
        return mapper.convertValue(saveProduct, ProductDto.class);
    }

    @Override
    public List<ProductDto> getAllProduct(Integer pageNumber, String searchKey) {
        List<ProductDto> productDtoList = new ArrayList<>();
        Pageable pageRequest = PageRequest.of(pageNumber, 10);
        if (searchKey.equals("")) {
            productDao.findAll(pageRequest).forEach(product -> {
                if (product instanceof Product)
                    productDtoList.add(mapper.convertValue(product, ProductDto.class));
            });
        } else {

            List<Product> products = productDao.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(searchKey, searchKey, pageRequest);
            if (products == null)
                return productDtoList;
            products.forEach(product ->
                    productDtoList.add(mapper.convertValue(product, ProductDto.class))
            );

        }
        return productDtoList;
    }

    @Override
    public boolean deleteProductDetails(Integer id) {
        if (productDao.findById(id).isPresent()) {
            productDao.deleteById(id);
            return true;
        }

        return false;



    }

    @Override
    public ProductDto getProductDetailById(Integer productId) {
        Product product = productDao.findById(productId).orElse(null);
        if (product != null)
            return mapper.convertValue(product, ProductDto.class);

        return null;
    }

    @Override
    public List<ProductDto> getProductDetail(boolean isSingleProductDetail, Integer productId) {
        if (isSingleProductDetail && productId != 0) {
            List<ProductDto> list = new ArrayList<>();
            Product product = productDao.findById(productId).orElse(null);
            if (product != null)
                list.add(mapper.convertValue(product, ProductDto.class));

            return list;
        } else {
            String currentUser = jwtRequestFilter.getCurrentUser();
            User user = userDao.findById(currentUser).orElse(null);
            if (user != null) {
                List<Cart> byUserCartList = cartDao.findByUser(user);

                return byUserCartList.stream().map(x -> mapper.convertValue(x.getProduct(), ProductDto.class)).collect(Collectors.toList());
            }

        }

        return new ArrayList<>();
    }
}
