package com.MindForum.version1.DTO.request;

import lombok.Getter;

import java.util.List;

@Getter
public class UpdateQuestionRequest {
    private String newTitle;
    private String newContent;
}
