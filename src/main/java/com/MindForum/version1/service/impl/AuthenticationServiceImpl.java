package com.MindForum.version1.service.impl;

import com.MindForum.version1.DTO.repsonse.AuthenticationResponse;
import com.MindForum.version1.DTO.request.*;
import com.MindForum.version1.entity.Role;
import com.MindForum.version1.entity.User;
import com.MindForum.version1.repository.RoleRepository;
import com.MindForum.version1.repository.UserRepository;
import com.MindForum.version1.service.AuthenticationService;
import com.MindForum.version1.service.JwtService;
import com.MindForum.version1.service.MailSenderService;
import com.MindForum.version1.util.RoleType;
import com.MindForum.version1.util.TokenType;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final MailSenderService mailSenderService;
    private final StringRedisTemplate redisTemplate;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
        ));
        User user = (User) authentication.getPrincipal();

        String accessToken = jwtService.generateToken(TokenType.ACCESS, user);
        String refreshToken = jwtService.generateToken(TokenType.REFRESH, user);

        log.info("User id {} is authenticated", user.getId());

        return AuthenticationResponse.builder()
                .name(user.getName())
                .username(user.getUsername())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void createUser(CreateUserRequest createUserRequest) {
        if (userRepository.existsByUsername(createUserRequest.getUsername()))
            throw new RuntimeException("Username already exists");

        Role role = roleRepository.findByRoleName(RoleType.USER.name())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        User user = User.builder()
                .name(createUserRequest.getName())
                .username(createUserRequest.getUsername())
                .password(passwordEncoder.encode(createUserRequest.getPassword()))
                .email(createUserRequest.getEmail())
                .isLock(Boolean.FALSE)
                .roles(Set.of(role))
                .build();

        userRepository.save(user);
        log.info("Created user id {}", user.getId());
    }

    @Override
    public void updatePassword(UserUpdatePasswordRequest userUpdatePasswordRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userUpdatePasswordRequest.getUsername(),
                        userUpdatePasswordRequest.getPassword()
                )
        );

        User user = (User) authentication.getPrincipal();

        user.setPassword(passwordEncoder.encode(userUpdatePasswordRequest.getNewPassword()));

        userRepository.save(user);
        log.info("User id {} has changed password", user.getId());
    }

    @Override
    public void forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        User user = userRepository.findByUsername(forgotPasswordRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User don't exist"));

        if (forgotPasswordRequest.getEmail().equals(user.getEmail())) {
            String code = RandomStringUtils.randomAlphanumeric(5);

            String key = "verify:" + code;
            String value = user.getUsername();

            redisTemplate.opsForValue().set(key, value, Duration.ofMinutes(10));

            mailSenderService.sendEmailVerificationCode(forgotPasswordRequest.getEmail(), code);

            log.info("Verification email has been sent");
        } else throw new RuntimeException("Email is incorrect");
    }

    @Override
    public String verifyCode(String verifyCode) {
        String username = redisTemplate.opsForValue().get("verify:" + verifyCode);
        if (StringUtils.isNotEmpty(username) && StringUtils.isNotBlank(username)) {
            return jwtService.generateResetToken(username);
        } else throw new RuntimeException("Please enter the correct verification code");
    }

    @Override
    public void resetPassword(String resetToken, UserResetPassword userResetPassword) {
        Claims claimsResetToken = jwtService.verifyToken(TokenType.RESET, resetToken);

        String username = claimsResetToken.getSubject();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(userResetPassword.getNewPassword()));

        userRepository.save(user);
        log.info("User id {} reset password", user.getId());
    }
}