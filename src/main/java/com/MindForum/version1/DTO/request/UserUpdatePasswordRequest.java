package com.MindForum.version1.DTO.request;

import lombok.Getter;

@Getter
public class UserUpdatePasswordRequest {
    private String username;
    private String password;
    private String newPassword;
}
