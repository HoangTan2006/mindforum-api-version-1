package com.MindForum.version1.DTO.request;

import lombok.Getter;

@Getter
public class ForgotPasswordRequest {
    private String username;
    private String email;
}
