package com.sliat.crm.ecommerce.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sliat.crm.ecommerce.dao.UserDao;
import com.sliat.crm.ecommerce.dto.JwtRequest;
import com.sliat.crm.ecommerce.dto.JwtResponse;
import com.sliat.crm.ecommerce.dto.UserDto;
import com.sliat.crm.ecommerce.entity.User;
import com.sliat.crm.ecommerce.service.JwtService;
import com.sliat.crm.ecommerce.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class JwtServiceImpl implements JwtService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findById(username).orElse(null);
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    getAuthoroties(user)
            );
        } else {
            throw new UsernameNotFoundException("username is invalid");
        }

    }

    private Set getAuthoroties(User user) {
        Set authorities = new HashSet();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });

        return authorities;
    }

    @Override
    public JwtResponse createJwtToken(JwtRequest jwtRequest) {
        String username = jwtRequest.getUsername();
        String userPassword = jwtRequest.getUserPassword();
        authenticate(username, userPassword);
        UserDetails userDetails = jwtService.loadUserByUsername(username);
        String newGenerateToken = jwtUtil.generateJwtToken(userDetails);

        User user = userDao.findById(username).orElse(null);
        UserDto userDto = null;
        if (user != null)
            userDto = mapper.convertValue(user, UserDto.class);

        return new JwtResponse(userDto, newGenerateToken);

    }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            System.out.println("DISABLE_USER" + e);
        } catch (BadCredentialsException e) {
            System.out.println("BAD CREDENTIAL_" + e);
        }

    }
}
