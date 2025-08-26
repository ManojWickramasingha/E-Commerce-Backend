package com.sliat.crm.ecommerce.controller;

import com.sliat.crm.ecommerce.dto.OrderInputDto;
import com.sliat.crm.ecommerce.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @PreAuthorize("hasRole('user')")
    @PostMapping("/placeOrder")
    public void placeOrder(@RequestBody OrderInputDto orderInputDto) {
        orderDetailService.placeOrder(orderInputDto);
    }
}
