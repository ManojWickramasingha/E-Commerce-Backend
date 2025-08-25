package com.sliat.crm.ecommerce.dto;

import com.sliat.crm.ecommerce.entity.ImageModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ProductDto {
    private Integer id;
    private String name;
    private String description;
    private Double discountedPrice;
    private Double actualPrice;
    private Set<ImageModel> productImages;
}
