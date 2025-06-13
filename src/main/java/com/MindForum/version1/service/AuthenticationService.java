package com.MindForum.version1.service;

import com.MindForum.version1.DTO.repsonse.AuthenticationResponse;
import com.MindForum.version1.DTO.request.*;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
    void createUser(CreateUserRequest createUserRequest);
    void updatePassword(UserUpdatePasswordRequest userUpdatePasswordRequest);
    void forgotPassword(ForgotPasswordRequest forgotPasswordRequest);
    String verifyCode(String verifyCode);
    void resetPassword(String resetToken, UserResetPassword userResetPassword);
}
