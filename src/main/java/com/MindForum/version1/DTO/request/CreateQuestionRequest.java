package com.MindForum.version1.DTO.request;

import lombok.Getter;

import java.util.List;

@Getter
public class CreateQuestionRequest {
    private String title;
    private String content;
    private List<String> tags;
}
