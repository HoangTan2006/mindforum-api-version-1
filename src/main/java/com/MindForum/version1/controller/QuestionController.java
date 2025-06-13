package com.MindForum.version1.controller;

import com.MindForum.version1.DTO.repsonse.QuestionDetailResponse;
import com.MindForum.version1.DTO.repsonse.QuestionResponse;
import com.MindForum.version1.DTO.repsonse.ResponseApi;
import com.MindForum.version1.DTO.request.CreateQuestionRequest;
import com.MindForum.version1.DTO.request.UpdateQuestionRequest;
import com.MindForum.version1.entity.User;
import com.MindForum.version1.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/v1/question")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseApi<Void> createQuestion(
            Authentication authentication,
            @RequestBody CreateQuestionRequest createQuestionRequest) {
        questionService.createQuestion((User) authentication.getPrincipal(), createQuestionRequest);

        return ResponseApi.<Void>builder()
                .statusCode(HttpStatus.CREATED.value())
                .timestamp(Instant.now())
                .message("Success")
                .data(null)
                .build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseApi<List<QuestionResponse>> getQuestions(
            @RequestParam Integer pageNumber,
            @RequestParam Integer pageSize) {
        List<QuestionResponse> questionResponses = questionService.getQuestions(pageNumber, pageSize);

        return ResponseApi.<List<QuestionResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .timestamp(Instant.now())
                .message("Success")
                .data(questionResponses)
                .build();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseApi<QuestionDetailResponse> getQuestionDetail(@PathVariable(name = "id") Long id) {
        QuestionDetailResponse questionDetail = questionService.getQuestionDetail(id);

        return ResponseApi.<QuestionDetailResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .timestamp(Instant.now())
                .message("Success")
                .data(questionDetail)
                .build();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseApi<Void> updateQuestion(
            Authentication authentication,
            @PathVariable(name = "id") Long id,
            @RequestBody UpdateQuestionRequest updateQuestionRequest) {

        questionService.updateQuestion((User) authentication.getPrincipal(), id, updateQuestionRequest);

        return ResponseApi.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .timestamp(Instant.now())
                .message("Success")
                .data(null)
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseApi<Void> deleteQuestion(
            Authentication authentication,
            @PathVariable(name = "id") Long id) {
        questionService.deleteQuestion((User) authentication.getPrincipal(), id);

        return ResponseApi.<Void>builder()
                .statusCode(HttpStatus.CREATED.value())
                .timestamp(Instant.now())
                .message("Success")
                .data(null)
                .build();
    }
}
