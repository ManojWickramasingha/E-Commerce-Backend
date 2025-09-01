package com.sliat.crm.ecommerce.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sliat.crm.ecommerce.configuration.JwtRequestFilter;
import com.sliat.crm.ecommerce.dao.OrderDetailDao;
import com.sliat.crm.ecommerce.dao.ProductDao;
import com.sliat.crm.ecommerce.dao.UserDao;
import com.sliat.crm.ecommerce.dto.OrderDetailDto;

import com.sliat.crm.ecommerce.dto.OrderInputDto;
import com.sliat.crm.ecommerce.dto.OrderProductQuantity;
import com.sliat.crm.ecommerce.entity.OrderDetail;
import com.sliat.crm.ecommerce.entity.Product;
import com.sliat.crm.ecommerce.entity.User;
import com.sliat.crm.ecommerce.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {

    private static final String PLACE_ORDER = "Placed";

    private final ObjectMapper mapper;

    private final ProductDao productDao;

    private final UserDao userDao;

    private final OrderDetailDao orderDetailDao;

    private final JwtRequestFilter jwtRequestFilter;


    @Override
    public void placeOrder(OrderInputDto orderInput) {



        String currentUser = jwtRequestFilter.getCurrentUser();
        List<OrderProductQuantity> orderProductQuantityList = orderInput.getProductQuantityList();
        Optional<User> opUser = userDao.findById(currentUser);
        User user = null;

        if (opUser.isPresent())
            user = opUser.get();


        for (OrderProductQuantity o : orderProductQuantityList) {
            Product product = null;
            Optional<Product> opProduct = productDao.findById(o.getProductId());
            if (opProduct.isPresent()) {
                product = opProduct.get();

                OrderDetailDto orderDetailDto = new OrderDetailDto(
                        orderInput.getFullName(),
                        orderInput.getFullAddress(),
                        orderInput.getContactNumber(),
                        orderInput.getAlternateContactNumber(),
                        PLACE_ORDER,
                        product.getActualPrice() * o.getQuantity(),

                        user,
                        product
                );
                OrderDetail orderDetail = mapper.convertValue(orderDetailDto, OrderDetail.class);

                orderDetailDao.save(orderDetail);
            }
        }

    }
}
