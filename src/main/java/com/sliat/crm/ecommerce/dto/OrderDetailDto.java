package com.sliat.crm.ecommerce.dto;

import com.sliat.crm.ecommerce.entity.Product;
import com.sliat.crm.ecommerce.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderDetailDto {
    private String fullName;
    private String fullOrder;
    private String contactNumber;
    private String alternateContactNumber;
    private String status;
    private Double amount;
    private User user;
    private Product product;
}
