package com.MindForum.version1.service.impl;

import com.MindForum.version1.entity.Role;
import com.MindForum.version1.entity.User;
import com.MindForum.version1.service.JwtService;
import com.MindForum.version1.util.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${secret_key.access}")
    private String SECRET_KEY_ACCESS;

    @Value("${secret_key.refresh}")
    private String SECRET_KEY_REFRESH;

    @Value("${secret_key.reset}")
    private String SECRET_KEY_RESET;

    @Value("${expiration_time.access}")
    private Long EXPIRATION_TIME_ACCESS;

    @Value("${expiration_time.refresh}")
    private Long EXPIRATION_TIME_REFRESH;

    @Value("${expiration_time.reset}")
    private Long EXPIRATION_TIME_RESET;

    @Value("${token.issuer}")
    private String ISSUER;

    @Override
    public String generateToken(TokenType tokenType, User user) {

        return Jwts.builder()
                .subject(user.getUsername()) //Set username
                .claim("roles", getRole(user)) //Set roles
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + getExpirationTime(tokenType)))
                .issuer(ISSUER)
                .signWith(getSecretKey(tokenType))
                .compact();
    }

    @Override
    public String generateResetToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + getExpirationTime(TokenType.RESET)))
                .issuer(ISSUER)
                .signWith(getSecretKey(TokenType.RESET))
                .compact();
    }

    @Override
    public Claims verifyToken(TokenType tokenType, String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey(tokenType))
                .requireIssuer(ISSUER)
                .build()
                .parseClaimsJws(token)
                .getPayload();
    }

    private SecretKey getSecretKey(TokenType tokenType) {
        switch (tokenType) {
            case TokenType.ACCESS -> {
                return Keys.hmacShaKeyFor(SECRET_KEY_ACCESS.getBytes());
            }
            case TokenType.REFRESH -> {
                return Keys.hmacShaKeyFor(SECRET_KEY_REFRESH.getBytes());
            }
            case TokenType.RESET -> {
                return Keys.hmacShaKeyFor(SECRET_KEY_RESET.getBytes());
            }
            default -> throw new IllegalStateException("Unexpected value: " + tokenType);
        }
    }

    private Long getExpirationTime(TokenType tokenType) {
        switch (tokenType) {
            case TokenType.ACCESS -> {
                return EXPIRATION_TIME_ACCESS;
            }
            case TokenType.REFRESH -> {
                return EXPIRATION_TIME_REFRESH;
            }
            case TokenType.RESET -> {
                return EXPIRATION_TIME_RESET;
            }
            default -> throw new IllegalStateException("Unexpected value: " + tokenType);
        }

    }

    private List<String> getRole(User user) {
        return user.getRoles()
                .stream()
                .map(Role::getRoleName).toList();
    }
}
