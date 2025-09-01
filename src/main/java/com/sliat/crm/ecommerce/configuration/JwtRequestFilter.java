package com.sliat.crm.ecommerce.configuration;

import com.sliat.crm.ecommerce.service.JwtService;
import com.sliat.crm.ecommerce.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@Slf4j
@RequiredArgsConstructor

public class JwtRequestFilter extends OncePerRequestFilter {


    private final JwtUtil jwtUtil;
    @Autowired
    private JwtService jwtService;


    public static String CURRENT_USER = "";
    private final JwtUtil jwtUtil;
  

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authorization = httpServletRequest.getHeader("Authorization");
        String jwtToken = null;
        String username = null;
        if (authorization != null && authorization.startsWith("Bearer ")) {
            jwtToken = authorization.substring(7);
            try {
                username = jwtUtil.getTokenFromUsername(jwtToken);



                CURRENT_USER = username;



            } catch (IllegalArgumentException e) {
                log.debug("unable to get Token");
            } catch (ExpiredJwtException e) {
                log.debug(" Jwt token has expired.");
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }


        } else {
            log.info("This Token does not exist Bearer");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = jwtService.loadUserByUsername(username);

            if (jwtUtil.isValidateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }

    public String getCurrentUser() {
        return currentUser;
    }
}
