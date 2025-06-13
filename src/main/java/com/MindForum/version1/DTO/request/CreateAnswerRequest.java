package com.MindForum.version1.DTO.request;

import lombok.Getter;

@Getter
public class CreateAnswerRequest {
    private Long questionId;
    private String content;
}
