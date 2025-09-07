package com.sliat.crm.ecommerce.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sliat.crm.ecommerce.configuration.JwtRequestFilter;
import com.sliat.crm.ecommerce.dao.CartDao;
import com.sliat.crm.ecommerce.dao.OrderDetailDao;
import com.sliat.crm.ecommerce.dao.ProductDao;
import com.sliat.crm.ecommerce.dao.UserDao;
import com.sliat.crm.ecommerce.dto.OrderDetailDto;
import com.sliat.crm.ecommerce.dto.OrderInputDto;
import com.sliat.crm.ecommerce.dto.OrderProductQuantity;
import com.sliat.crm.ecommerce.entity.Cart;
import com.sliat.crm.ecommerce.entity.OrderDetail;
import com.sliat.crm.ecommerce.entity.Product;
import com.sliat.crm.ecommerce.entity.User;
import com.sliat.crm.ecommerce.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {

    private static final String PLACE_ORDER = "Placed";

    private final ObjectMapper mapper;

    private final ProductDao productDao;

    private final UserDao userDao;

    private final OrderDetailDao orderDetailDao;

    private final JwtRequestFilter jwtRequestFilter;

    private final CartDao cartDao;

    @Override
    public void placeOrder(OrderInputDto orderInput, boolean isSingleProductCheckOut) {


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

        if (!isSingleProductCheckOut) {
            List<Cart> byUserCartList = cartDao.findByUser(user);
            byUserCartList.stream().forEach(x -> cartDao.deleteById(x.getId()));
        }

    }

    @Override
    public List<OrderDetailDto> getOrderDetails() {

        String currentUser = jwtRequestFilter.getCurrentUser();
        User user = userDao.findById(currentUser).orElse(null);
        if (user != null) {
            List<OrderDetail> byUserOrderList = orderDetailDao.findByUser(user);
            return byUserOrderList.stream().map(orderDetail -> mapper.convertValue(orderDetail, OrderDetailDto.class)).collect(Collectors.toList());

        }

        return new ArrayList<>();
    }

    @Override
    public List<OrderDetail> getAllOrder() {
        List<OrderDetail> allOrder = new ArrayList<>();
        orderDetailDao.findAll().forEach(
                allOrder::add
        );

        return allOrder;
    }

    @Override
    public void markOrderDelivered(Integer orderId) {
        OrderDetail orderDetail = orderDetailDao.findById(orderId).orElse(null);
        if (orderDetail != null) {
            orderDetail.setStatus("Delivered");
            orderDetailDao.save(orderDetail);
        }

    }
}
