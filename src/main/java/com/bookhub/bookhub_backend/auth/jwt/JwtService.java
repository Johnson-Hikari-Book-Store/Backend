package com.bookhub.bookhub_backend.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${spring.application.jwt.secret-key}")
    private String JWT_SECRET;

    @Value("${spring.application.jwt.expiration-time}")
    private Long JWT_EXPIRY_TIME;

    public String getUsername(String token) {
        final Claims claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJwt(token).getBody();
        return claims.getSubject();
    }

    private Date getExpiration(String token) {
        final Claims claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJwt(token).getBody();
        return claims.getExpiration();
    }

    public String generateJwtToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (JWT_EXPIRY_TIME * 1000)))
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
    }

    protected Boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsername(token);
        System.out.println(isTokenExpired(token));
        return (username.equals(userDetails.getUsername())) && isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).after(new Date());
    }

}
