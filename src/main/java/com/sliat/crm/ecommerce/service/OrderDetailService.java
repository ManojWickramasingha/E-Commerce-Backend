package com.sliat.crm.ecommerce.service;

import com.sliat.crm.ecommerce.dto.OrderDetailDto;
import com.sliat.crm.ecommerce.dto.OrderInputDto;

import java.util.List;

public interface OrderDetailService {
    void placeOrder(OrderInputDto orderInputDto, boolean isSingleProductCheckOut);

    List<OrderDetailDto> getOrderDetails();
}
