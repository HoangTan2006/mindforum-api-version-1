package com.MindForum.version1.exception;

import com.MindForum.version1.DTO.repsonse.ResponseApi;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseApi<String> exceptionHandle(RuntimeException re) {
        return  ResponseApi.<String>builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(Instant.now())
                .message("Error")
                .data(re.getMessage())
                .build();
    }
}
