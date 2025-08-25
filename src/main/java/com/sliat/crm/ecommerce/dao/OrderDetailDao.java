package com.sliat.crm.ecommerce.dao;

import com.sliat.crm.ecommerce.entity.OrderDetail;
import org.springframework.data.repository.CrudRepository;

public interface OrderDetailDao extends CrudRepository<OrderDetail, Integer> {
}
