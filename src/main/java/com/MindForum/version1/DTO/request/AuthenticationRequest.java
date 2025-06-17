package com.MindForum.version1.DTO.request;

import lombok.Getter;

@Getter
public class AuthenticationRequest {
    private String username;
    private String password;
}