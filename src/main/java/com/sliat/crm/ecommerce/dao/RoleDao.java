package com.sliat.crm.ecommerce.dao;

import com.sliat.crm.ecommerce.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, String> {
}
