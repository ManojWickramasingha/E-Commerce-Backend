package com.sliat.crm.ecommerce.service;

import com.sliat.crm.ecommerce.dto.OrderInputDto;

public interface OrderDetailService {
    void placeOrder(OrderInputDto orderInputDto, boolean isSingleProductCheckOut);
}
