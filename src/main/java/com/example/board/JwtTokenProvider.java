package com.example.board;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    private final Key key;  // secret key for signing JWT
    private final long accessTokenExpirationTime;  // Access token expiration time
    private final long refreshTokenExpirationTime;  // Refresh token expiration time

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.accessToken.expiration.time}") long accessTokenExpirationTime,
            @Value("${jwt.refreshToken.expiration.time}") long refreshTokenExpirationTime
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpirationTime = accessTokenExpirationTime;
        this.refreshTokenExpirationTime = refreshTokenExpirationTime;
    }

    // 액세스 토큰 생성
    public String generateAccessToken(String userId) {
        Claims claims = Jwts.claims().setSubject(userId);

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + accessTokenExpirationTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 리프레시 토큰 생성
    public String generateRefreshToken(String userId) {
        Claims claims = Jwts.claims().setSubject(userId);

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + refreshTokenExpirationTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // 리프레시 토큰 유효성 검사
    public boolean validateRefreshToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // 토큰에서 사용자 ID 추출
    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    // 리프레시 토큰에서 사용자 ID 추출
    public String getUserIdFromRefreshToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}