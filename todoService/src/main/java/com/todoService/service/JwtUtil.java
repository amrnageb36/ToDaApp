package com.todoService.service;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;

@Service
public class JwtUtil {

    private String secretKey = "mysecretkey"; // your secret key

    public String extractSubject(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
