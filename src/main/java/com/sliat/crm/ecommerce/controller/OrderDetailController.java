package com.sliat.crm.ecommerce.controller;

import com.sliat.crm.ecommerce.dto.OrderDetailDto;
import com.sliat.crm.ecommerce.dto.OrderInputDto;
import com.sliat.crm.ecommerce.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class OrderDetailController {


    private final OrderDetailService orderDetailService;

    @PreAuthorize("hasRole('user')")
    @PostMapping("/placeOrder/{isSingleProductCheckOut}")
    public void placeOrder(@RequestBody OrderInputDto orderInputDto, @PathVariable("isSingleProductCheckOut") boolean isSingleProductCheckOut) {
        orderDetailService.placeOrder(orderInputDto, isSingleProductCheckOut);
    }

    @PreAuthorize("hasRole('user')")
    @GetMapping("/getOrderDetails")
    public List<OrderDetailDto> getOrderDetails() {
        return orderDetailService.getOrderDetails();
    }
}
