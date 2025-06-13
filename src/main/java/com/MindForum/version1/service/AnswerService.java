package com.MindForum.version1.service;

import com.MindForum.version1.DTO.request.CreateAnswerRequest;
import com.MindForum.version1.DTO.request.UpdateAnswerRequest;
import com.MindForum.version1.entity.User;

public interface AnswerService {
    void createAnswer(CreateAnswerRequest createAnswerRequest, User user);
    void updateAnswer(User user, Long id, UpdateAnswerRequest updateAnswerRequest);
    void deleteAnswer(User user, Long id);
}
