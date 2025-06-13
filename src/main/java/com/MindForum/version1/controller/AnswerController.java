package com.MindForum.version1.controller;

import com.MindForum.version1.DTO.repsonse.ResponseApi;
import com.MindForum.version1.DTO.request.CreateAnswerRequest;
import com.MindForum.version1.DTO.request.UpdateAnswerRequest;
import com.MindForum.version1.entity.User;
import com.MindForum.version1.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/answer")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseApi<Void> createAnswer(
            @RequestBody CreateAnswerRequest createAnswerRequest,
            Authentication authentication) {

        answerService.createAnswer(createAnswerRequest, (User) authentication.getPrincipal());

        return ResponseApi.<Void>builder()
                .statusCode(HttpStatus.CREATED.value())
                .timestamp(Instant.now())
                .message("Success")
                .data(null)
                .build();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseApi<Void> updateAnswer(
            Authentication authentication,
            @PathVariable(name = "id") Long id,
            UpdateAnswerRequest updateAnswerRequest) {

        answerService.updateAnswer((User) authentication.getPrincipal(), id, updateAnswerRequest);

        return ResponseApi.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .timestamp(Instant.now())
                .message("Success")
                .data(null)
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseApi<Void> deleteAnswer(
            Authentication authentication,
            @PathVariable(name = "id") Long id) {

        answerService.deleteAnswer((User) authentication.getPrincipal(), id);

        return ResponseApi.<Void>builder()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .timestamp(Instant.now())
                .message("Success")
                .data(null)
                .build();
    }
}
