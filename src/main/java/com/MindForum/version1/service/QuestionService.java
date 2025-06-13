package com.MindForum.version1.service;

import com.MindForum.version1.DTO.repsonse.QuestionDetailResponse;
import com.MindForum.version1.DTO.repsonse.QuestionResponse;
import com.MindForum.version1.DTO.request.CreateQuestionRequest;
import com.MindForum.version1.DTO.request.UpdateQuestionRequest;
import com.MindForum.version1.entity.User;

import java.util.List;

public interface QuestionService {
    QuestionDetailResponse getQuestionDetail(Long questionId);
    List<QuestionResponse> getQuestions(Integer pageNumber, Integer pageSize);
    void updateQuestion(User user, Long id, UpdateQuestionRequest updateQuestionRequest);
    void createQuestion(User user, CreateQuestionRequest createQuestionRequest);
    void deleteQuestion(User user, Long id);
}
