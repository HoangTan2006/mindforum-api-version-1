package com.MindForum.version1.controller;

import com.MindForum.version1.DTO.repsonse.AuthenticationResponse;
import com.MindForum.version1.DTO.repsonse.ResetTokenResponse;
import com.MindForum.version1.DTO.repsonse.ResponseApi;
import com.MindForum.version1.DTO.request.*;
import com.MindForum.version1.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public ResponseApi<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(authenticationRequest);

        return ResponseApi.<AuthenticationResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .timestamp(Instant.now())
                .message("Success")
                .data(authenticationResponse)
                .build();
    }

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseApi<Void> createUser(@RequestBody CreateUserRequest createUserRequest) {
        authenticationService.createUser(createUserRequest);

        return ResponseApi.<Void>builder()
                .statusCode(HttpStatus.CREATED.value())
                .timestamp(Instant.now())
                .message("Success")
                .data(null)
                .build();
    }

    @PostMapping("update-password")
    @ResponseStatus(HttpStatus.OK)
    public ResponseApi<Void> updatePassword(@RequestBody UserUpdatePasswordRequest userUpdatePasswordRequest) {
        authenticationService.updatePassword(userUpdatePasswordRequest);

        return ResponseApi.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .timestamp(Instant.now())
                .message("Success")
                .data(null)
                .build();
    }

    @PostMapping("/forgot-password")
    @ResponseStatus(HttpStatus.OK)
    public ResponseApi<Void> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        authenticationService.forgotPassword(forgotPasswordRequest);

        return ResponseApi.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .timestamp(Instant.now())
                .message("Success")
                .data(null)
                .build();
    }

    @PostMapping("/verify-code")
    @ResponseStatus(HttpStatus.OK)
    public ResponseApi<ResetTokenResponse> verifyCode(@RequestBody VerifyCodeRequest verifyCodeRequest) {
        String resetToken = authenticationService.verifyCode(verifyCodeRequest.getVerifyCode());
        ResetTokenResponse resetTokenResponse = new ResetTokenResponse(resetToken);

        return ResponseApi.<ResetTokenResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .timestamp(Instant.now())
                .message("Success")
                .data(resetTokenResponse)
                .build();
    }

    @PostMapping("/reset-password")
    @ResponseStatus(HttpStatus.OK)
    public ResponseApi<Void> resetPassword(
            @RequestHeader(name = "RT") String resetToken,
            @RequestBody UserResetPassword userResetPassword) {

        authenticationService.resetPassword(resetToken, userResetPassword);

        return ResponseApi.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .timestamp(Instant.now())
                .message("Success")
                .data(null)
                .build();
    }
}
