package com.MindForum.version1.service.impl;

import com.MindForum.version1.DTO.request.CreateUserRequest;
import com.MindForum.version1.DTO.request.UserUpdatePasswordRequest;
import com.MindForum.version1.entity.Role;
import com.MindForum.version1.entity.User;
import com.MindForum.version1.repository.RoleRepository;
import com.MindForum.version1.repository.UserRepository;
import com.MindForum.version1.service.UserService;
import com.MindForum.version1.util.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
