package com.sliat.crm.ecommerce.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor

public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String fullName;
    private String fullOrder;
    private String contactNumber;
    private String alternateContactNumber;
    private String status;
    private Double amount;
    @OneToOne
    private Product product;
    @OneToOne
    private User user;


    public OrderDetail(String fullName, String fullOrder, String contactNumber, String alternateContactNumber, String status, Double amount, Product product, User user) {
        this.fullName = fullName;
        this.fullOrder = fullOrder;
        this.contactNumber = contactNumber;
        this.alternateContactNumber = alternateContactNumber;
        this.status = status;
        this.amount = amount;
        this.product = product;
        this.user = user;
    }

}
