package com.MindForum.version1.DTO.repsonse;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class AuthenticationResponse {
    private String name;
    private String username;
    private String accessToken;
    private String refreshToken;
}
