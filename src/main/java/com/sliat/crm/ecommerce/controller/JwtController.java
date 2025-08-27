package com.sliat.crm.ecommerce.controller;

import com.sliat.crm.ecommerce.dto.JwtRequest;
import com.sliat.crm.ecommerce.dto.JwtResponse;
import com.sliat.crm.ecommerce.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class JwtController {


    private final JwtService jwtService;

    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponse> createJwtToken(@RequestBody JwtRequest jwtRequest) {
        JwtResponse res = jwtService.createJwtToken(jwtRequest);
        if (res != null)
            return ResponseEntity.ok(res);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
}
