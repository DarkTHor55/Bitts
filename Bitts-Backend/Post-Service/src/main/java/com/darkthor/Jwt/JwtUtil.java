package com.darkthor.Jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    // Method to extract all claims from JWT
    public Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (MalformedJwtException e) {
            throw new RuntimeException("Invalid JWT Token", e);
        }
    }

    // Method to extract a specific claim (username)
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject(); // typically the username
    }

    // Method to extract a custom claim (userId or email)
    public Long extractUserId(String token) {
        Object userIdClaim = extractAllClaims(token).get("userId");
        if (userIdClaim instanceof String) {
            return Long.valueOf((String) userIdClaim);
        } else if (userIdClaim instanceof Number) {
            return ((Number) userIdClaim).longValue();
        }
        throw new RuntimeException("Invalid userId claim in JWT token");
    }

    // Method to extract email if the token stores it
    public String extractEmail(String token) {
        return (String) extractAllClaims(token).get("email");
    }
}
