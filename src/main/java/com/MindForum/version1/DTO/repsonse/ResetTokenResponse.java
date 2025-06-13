package com.MindForum.version1.DTO.repsonse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class ResetTokenResponse {
    private String resetToken;
}
