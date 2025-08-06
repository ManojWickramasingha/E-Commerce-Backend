package com.sliat.crm.ecommerce.controller;

import com.sliat.crm.ecommerce.dto.RoleDto;
import com.sliat.crm.ecommerce.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleDto> createNewRole(@RequestBody RoleDto roleDto) {
        RoleDto role = roleService.createNewRole(roleDto);
        if (role != null)
            return ResponseEntity.ok(role);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}
