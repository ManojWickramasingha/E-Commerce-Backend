package com.sliat.crm.ecommerce.controller;

import com.sliat.crm.ecommerce.dto.UserDto;
import com.sliat.crm.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userData) {
        UserDto user = userService.registerUser(userData);
        if (user != null)
            return ResponseEntity.ok(user);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @GetMapping("/forUser")
    @PreAuthorize("hasRole('user')")
    public String forUser() {
        return "User access only";
    }

    @GetMapping("forAdmin")
    @PreAuthorize("hasRole('admin')")
    public String forAdmin() {
        return "Admin access only";
    }
}
