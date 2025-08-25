package com.sliat.crm.ecommerce.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sliat.crm.ecommerce.configuration.JwtRequestFilter;
import com.sliat.crm.ecommerce.dao.OrderDetailDao;
import com.sliat.crm.ecommerce.dao.ProductDao;
import com.sliat.crm.ecommerce.dao.UserDao;
import com.sliat.crm.ecommerce.dto.OrderInputDto;
import com.sliat.crm.ecommerce.dto.OrderProductQuantity;
import com.sliat.crm.ecommerce.entity.OrderDetail;
import com.sliat.crm.ecommerce.entity.Product;
import com.sliat.crm.ecommerce.entity.User;
import com.sliat.crm.ecommerce.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    private static final String PLACE_ORDER = "Placed";
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private OrderDetailDao orderDetailDao;

    @Override
    public void placeOrder(OrderInputDto orderInput) {


        String current_user = JwtRequestFilter.CURRENT_USER;
        List<OrderProductQuantity> orderProductQuantityList = orderInput.getProductQuantityList();
        User user = userDao.findById(current_user).get();
        for (OrderProductQuantity o : orderProductQuantityList) {
            Product product = null;
            if (productDao.findById(o.getProductId()).isPresent()) {
                product = productDao.findById(o.getProductId()).get();

                OrderDetail orderDetail = new OrderDetail(
                        orderInput.getFullName(),
                        orderInput.getFullAddress(),
                        orderInput.getContactNumber(),
                        orderInput.getAlternateContactNumber(),
                        PLACE_ORDER,
                        product.getActualPrice() * o.getQuantity(),
                        product,
                        user
                );

                orderDetailDao.save(orderDetail);
            }
        }

    }
}
