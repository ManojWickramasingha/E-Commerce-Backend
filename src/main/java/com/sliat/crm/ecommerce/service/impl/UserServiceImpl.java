package com.sliat.crm.ecommerce.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sliat.crm.ecommerce.dao.RoleDao;
import com.sliat.crm.ecommerce.dao.UserDao;
import com.sliat.crm.ecommerce.dto.UserDto;
import com.sliat.crm.ecommerce.entity.Role;
import com.sliat.crm.ecommerce.entity.User;
import com.sliat.crm.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto registerUser(UserDto userData) {
        User user = mapper.convertValue(userData, User.class);
        Set<Role> roles = new HashSet<>();
        Role role = roleDao.findById("admin").orElse(null);
        roles.add(role);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User saveUser = userDao.save(user);
        return mapper.convertValue(saveUser, UserDto.class);
    }
}
