package com.MindForum.version1.service;

import com.MindForum.version1.entity.User;
import com.MindForum.version1.util.TokenType;
import io.jsonwebtoken.Claims;

public interface JwtService {
    String generateToken(TokenType tokenType, User user);
    String generateResetToken(String username);
    Claims verifyToken(TokenType tokenType, String token);
}
