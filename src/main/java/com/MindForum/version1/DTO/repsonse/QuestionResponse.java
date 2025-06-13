package com.MindForum.version1.DTO.repsonse;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Setter
@Getter
@Builder
public class QuestionResponse {
    private Long id;
    private UserResponse author;
    private String title;
    private String content;
    private List<TagResponse> tags;
    private Instant createdAt;
}
