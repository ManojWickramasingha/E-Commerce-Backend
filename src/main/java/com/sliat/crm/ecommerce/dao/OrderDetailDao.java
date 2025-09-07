package com.sliat.crm.ecommerce.dao;

import com.sliat.crm.ecommerce.entity.OrderDetail;
import com.sliat.crm.ecommerce.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderDetailDao extends CrudRepository<OrderDetail, Integer> {

    List<OrderDetail> findByUser(User user);

    List<OrderDetail> findByStatus(String status);
}
