package com.zio.common.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.PublicKey;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey secretKey;

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String buildToken(Long userId) {
        return Jwts.builder()
                .signWith(secretKey)
                .subject(userId.toString())
                .expiration(Date.from(Instant.now().plus(Duration.ofDays(30L))))
                .compact();
    }

    public Long getUserId(String jwt) {
        return Long.parseLong(getClaims(jwt).getSubject());
    }

    private Claims getClaims(String jwt) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(jwt).getPayload();
    }
}
