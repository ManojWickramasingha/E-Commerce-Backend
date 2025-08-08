package com.sliat.crm.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    private String name;
    private String description;
    private Double discountedPrice;
    private Double actualPrice;
}
