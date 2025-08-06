package com.sliat.crm.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRequest {
    private String username;
    private String userPassword;
}
