package com.MindForum.version1.DTO.request;

import lombok.*;

@Getter
public class CreateUserRequest {
    private String name;
    private String username;
    private String password;
    private String email;
}
