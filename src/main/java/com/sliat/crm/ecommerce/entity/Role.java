package com.sliat.crm.ecommerce.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Setter
@Getter
public class Role {
    @Id
    private String name;
    private String description;
}
