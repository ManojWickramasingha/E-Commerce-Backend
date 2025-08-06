package com.sliat.crm.ecommerce.dao;

import com.sliat.crm.ecommerce.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, String> {
}
