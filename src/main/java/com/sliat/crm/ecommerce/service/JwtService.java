package com.sliat.crm.ecommerce.service;

import com.sliat.crm.ecommerce.dto.JwtRequest;
import com.sliat.crm.ecommerce.dto.JwtResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface JwtService extends UserDetailsService {

    JwtResponse createJwtToken(JwtRequest jwtRequest);
}
