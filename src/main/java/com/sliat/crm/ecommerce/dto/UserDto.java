package com.sliat.crm.ecommerce.dto;

import com.sliat.crm.ecommerce.entity.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserDto {
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private Set<Role> roles;
}
