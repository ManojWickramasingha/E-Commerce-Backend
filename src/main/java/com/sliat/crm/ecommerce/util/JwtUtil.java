package com.sliat.crm.ecommerce.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    private static final String secreatKey = "Manoj_wickramasingha";
    private static final int TOKEN_VALIDAITY = 3600 * 5;

    public String getTokenFromUsername(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
        Claims claims = getClaimsAllFromToken(token);
        return claimResolver.apply(claims);
    }

    private Claims getClaimsAllFromToken(String token) {
        return Jwts.parser().setSigningKey(secreatKey).parseClaimsJws(token).getBody();
    }

    public boolean isValidateToken(String token, UserDetails userDetails) {
        String username = getTokenFromUsername(token);
        return username.equals(userDetails.getUsername()) && !isExpired(token);
    }

    private Boolean isExpired(String token) {
        Date date = ExpiredFromToken(token);
        return date.before(new Date());
    }

    private Date ExpiredFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public String generateJwtToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDAITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secreatKey)
                .compact();
    }
}
