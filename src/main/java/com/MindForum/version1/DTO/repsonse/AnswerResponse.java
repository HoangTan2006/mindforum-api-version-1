package com.MindForum.version1.DTO.repsonse;

import com.MindForum.version1.entity.Question;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@Builder
public class AnswerResponse {
    private Long id;
    private UserResponse user;
    private String content;
    private Integer score;
    private Instant createdAt;
}
