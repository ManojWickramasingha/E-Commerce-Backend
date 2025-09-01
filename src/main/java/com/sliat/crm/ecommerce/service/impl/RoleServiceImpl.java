package com.sliat.crm.ecommerce.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sliat.crm.ecommerce.dao.RoleDao;
import com.sliat.crm.ecommerce.dto.RoleDto;
import com.sliat.crm.ecommerce.entity.Role;
import com.sliat.crm.ecommerce.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {


    private final RoleDao roleDao;

    private final ObjectMapper mapper;

    @Override
    public RoleDto createNewRole(RoleDto roleDto) {
        Role role = mapper.convertValue(roleDto, Role.class);
        Role saveRole = roleDao.save(role);
        return mapper.convertValue(saveRole, RoleDto.class);
    }
}
